package ru.demin.paging.storage

import ru.demin.paging.adapter.User

class UserStorage {
    fun loadUsers(startPosition: Int, loadSize: Int): List<User> {
        val users = arrayListOf<User>()
        if (startPosition > 0) {
            Thread.sleep(2000)
        }
        for (i in startPosition..(startPosition + loadSize - 1)) {
            users.add(User("user $i"))
        }
        return users
    }
}