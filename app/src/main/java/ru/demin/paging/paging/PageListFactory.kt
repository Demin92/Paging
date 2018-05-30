package ru.demin.paging.paging

import android.arch.paging.PagedList
import ru.demin.paging.adapter.User
import java.util.concurrent.Executors

class PageListFactory {
    companion object {
        private val PAGED_LIST_PAGE_SIZE = 10
    }

    fun createPageList(): PagedList<User> {
        return PagedList.Builder<Int, User>(UserDataSource(), createConfig())
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