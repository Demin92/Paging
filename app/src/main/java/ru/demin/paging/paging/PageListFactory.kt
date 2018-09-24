package ru.demin.paging.paging

import android.arch.paging.PagedList
import android.content.Context
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import ru.demin.paging.adapter.User
import ru.demin.paging.storage.NetworkStateSubject
import ru.demin.paging.storage.RetrySubject
import java.util.concurrent.Executors

class PageListFactory {
    companion object {
        private const val PAGED_LIST_PAGE_SIZE = 10
    }

    fun createPageList(networkStateSubject: NetworkStateSubject,
                       retrySubject: RetrySubject,
                       context: Context): PagedList<Item<ViewHolder>> {
        return PagedList.Builder<Int, Item<ViewHolder>>(UserDataSource(networkStateSubject, retrySubject, context), createConfig())
                .setNotifyExecutor(MainThreadExecutor())
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build()
    }

    private fun createConfig(): PagedList.Config {
        return PagedList.Config.Builder()
                .setPageSize(PAGED_LIST_PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build()
    }
}