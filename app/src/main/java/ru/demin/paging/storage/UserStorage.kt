package ru.demin.paging.storage

import android.util.Log
import io.reactivex.subjects.PublishSubject
import ru.demin.paging.adapter.User
import ru.demin.paging.paging.UserDataSource

class UserStorage {
    private val cache = arrayListOf<User>()
    private val deletedUsers = arrayListOf<User>()

    val onDeletePublishSubject = PublishSubject.create<Unit>()

    var totalCount = 500
        private set

    fun loadUsers(startPosition: Int, loadSize: Int): List<User> {
        val values = ArrayList<User>()

        val cacheSize = cache.size
        if (startPosition + loadSize <= cacheSize) {
            cache.mapIndexed { index, item -> if (index in startPosition..(startPosition + loadSize - 1)) values.add(item) }
            return values
        } else
            if (startPosition >= cacheSize) {
                return loadUsersFromServer(startPosition, loadSize)
            } else {
                val sublist = cache.subList(startPosition, cacheSize)
                values.addAll(sublist)
                values.addAll(loadUsersFromServer(cacheSize, loadSize - sublist.size))
                return values
            }
    }

    fun deleteUser(user: User) {
        cache.remove(user)
        totalCount--
        deletedUsers.add(user)
        onDeletePublishSubject.onNext(Unit)
    }

    private fun loadUsersFromServer(startPosition: Int, loadSize: Int): List<User> {
        val users = arrayListOf<User>()
        var i = startPosition
        while (users.size < loadSize) {
            if (cache.none {  it.id == i }) {
                Thread.sleep(100)
                users.add(User(i, "user $i"))
            } else {
                Log.d(UserDataSource.LOG_TAG, "already added i = $i ")
            }
            i++
        }
        cache.addAll(users)
        return users
    }
}