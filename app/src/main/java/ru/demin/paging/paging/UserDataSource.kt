package ru.demin.paging.paging

import android.arch.paging.PositionalDataSource
import android.content.Context
import android.util.Log
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import ru.demin.paging.adapter.LoadingState
import ru.demin.paging.storage.NetworkStateSubject
import ru.demin.paging.storage.RetrySubject
import ru.demin.paging.storage.UserStorage

class UserDataSource(private val networkStateSubject: NetworkStateSubject,
                     private val retrySubject: RetrySubject,
                     context: Context) : PositionalDataSource<Item<ViewHolder>>() {
    companion object {
        private const val LOG_TAG = "PagingLibrary"
        private const val FAKE_TOTAL_COUNT = 500
    }

    private val userStorage = UserStorage(context)

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Item<ViewHolder>>) {
        networkStateSubject.onNetworkStateChanged(LoadingState.LOADING)
        Log.d(LOG_TAG, "loadInitial requestedStartPosition = ${params.requestedStartPosition}, requestedLoadSize = ${params.requestedLoadSize}, " +
                "thread = ${Thread.currentThread().name}")
        userStorage.loadItems(params.requestedStartPosition, params.requestedLoadSize).subscribe({
            networkStateSubject.onNetworkStateChanged(LoadingState.LOADED)
            if (params.placeholdersEnabled) {
                callback.onResult(it, params.requestedStartPosition, FAKE_TOTAL_COUNT)
            } else {
                callback.onResult(it, params.requestedStartPosition)
            }
        }, {
            retrySubject.onRetryStateChanged { loadInitial(params, callback) }
            networkStateSubject.onNetworkStateChanged(LoadingState.ERROR)
        })
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Item<ViewHolder>>) {
        networkStateSubject.onNetworkStateChanged(LoadingState.LOADING)
        Log.d(LOG_TAG, "loadRange startPosition = ${params.startPosition}, loadSize = ${params.loadSize}, " +
                "thread = ${Thread.currentThread().name}")
        userStorage.loadItems(params.startPosition, params.loadSize)
                .subscribe({
                    networkStateSubject.onNetworkStateChanged(LoadingState.LOADED)
                    callback.onResult(it)
                }, {
                    retrySubject.onRetryStateChanged { loadRange(params, callback) }
                    networkStateSubject.onNetworkStateChanged(LoadingState.ERROR)
                })

    }
}