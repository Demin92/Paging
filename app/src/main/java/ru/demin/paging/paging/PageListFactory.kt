package ru.demin.paging.paging

import android.arch.paging.PagedList
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import ru.demin.paging.adapter.User
import java.util.concurrent.Executors

class PageListFactory {
    companion object {
        private const val PAGED_LIST_PAGE_SIZE = 10
    }

    fun createPageList(): PagedList<Item<ViewHolder>> {
        return PagedList.Builder<Int, Item<ViewHolder>>(UserDataSource(), createConfig())
                .setNotifyExecutor(MainThreadExecutor())
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build()
    }

    private fun createConfig(): PagedList.Config {
        return PagedList.Config.Builder()
                .setPageSize(PAGED_LIST_PAGE_SIZE)
                .setEnablePlaceholders(true)
                .build()
    }
}