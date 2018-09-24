package ru.demin.paging.paging

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import android.content.Context
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import io.reactivex.Observable
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
                       context: Context): Observable<PagedList<Item<ViewHolder>>> {
        return RxPagedListBuilder(object : DataSource.Factory<Int, Item<ViewHolder>>() {
            override fun create(): DataSource<Int, Item<ViewHolder>> {
                return UserDataSource(networkStateSubject, retrySubject, context)
            }
        }, createConfig()).buildObservable()
    }

    private fun createConfig(): PagedList.Config {
        return PagedList.Config.Builder()
                .setPageSize(PAGED_LIST_PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build()
    }
}