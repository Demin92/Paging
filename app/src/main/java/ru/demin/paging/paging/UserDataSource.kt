package ru.demin.paging.paging

import android.arch.paging.PositionalDataSource
import android.util.Log
import ru.demin.paging.adapter.User
import ru.demin.paging.storage.UserStorage

class UserDataSource(private val userStorage: UserStorage) : PositionalDataSource<User>() {
    companion object {
        const val LOG_TAG = "PagingLibrary"
    }

    init {
        userStorage.onDeletePublishSubject
                .subscribe {
                    invalidate()
                }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<User>) {
        Log.d(LOG_TAG, "loadInitial requestedStartPosition = ${params.requestedStartPosition}, requestedLoadSize = ${params.requestedLoadSize}" +
                " thread = ${Thread.currentThread().name}")
        val users = userStorage.loadUsers(params.requestedStartPosition, params.requestedLoadSize)
        if (params.placeholdersEnabled) {
            callback.onResult(users, params.requestedStartPosition, userStorage.totalCount)
        } else {
            callback.onResult(users, params.requestedStartPosition)

        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<User>) {
        Log.d(LOG_TAG, "loadRange startPosition = ${params.startPosition}, loadSize = ${params.loadSize}" +
                " thread = ${Thread.currentThread().name}")
        val users = userStorage.loadUsers(params.startPosition, params.loadSize)
        callback.onResult(users)
    }
}