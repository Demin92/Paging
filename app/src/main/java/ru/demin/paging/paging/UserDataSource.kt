package ru.demin.paging.paging

import android.arch.paging.PositionalDataSource
import android.util.Log
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import ru.demin.paging.adapter.User
import ru.demin.paging.storage.UserStorage

class UserDataSource: PositionalDataSource<Item<ViewHolder>>() {
    companion object {
        private const val LOG_TAG = "PagingLibrary"
        private const val FAKE_TOTAL_COUNT = 500
    }

    private val userStorage = UserStorage()

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Item<ViewHolder>>) {
        Log.d(LOG_TAG, "loadInitial requestedStartPosition = ${params.requestedStartPosition}, requestedLoadSize = ${params.requestedLoadSize}")
        val users = userStorage.loadItems(params.requestedStartPosition, params.requestedLoadSize)
        if (params.placeholdersEnabled) {
            callback.onResult(users, params.requestedStartPosition, FAKE_TOTAL_COUNT)
        } else {
            callback.onResult(users, params.requestedStartPosition)

        }
    }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Item<ViewHolder>>) {
        Log.d(LOG_TAG, "loadRange startPosition = ${params.startPosition}, loadSize = ${params.loadSize}")
        val users = userStorage.loadItems(params.startPosition, params.loadSize)
        callback.onResult(users)
    }
}