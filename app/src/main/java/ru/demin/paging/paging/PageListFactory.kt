package ru.demin.paging.paging

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.Observable
import ru.demin.paging.adapter.User
import ru.demin.paging.storage.UserStorage

class PageListFactory(private val userStorage: UserStorage) {
    companion object {
        private const val PAGED_LIST_PAGE_SIZE = 10
        private const val IS_PLACEHOLDERS_ENABLED = true
    }

    fun createPageList(): Observable<PagedList<User>> {
        return RxPagedListBuilder(object : DataSource.Factory<Int, User>() {
            override fun create(): DataSource<Int, User> {
                return UserDataSource(userStorage)
            }
        }, createConfig()).buildObservable()

    }

    private fun createConfig(): PagedList.Config {
        return PagedList.Config.Builder()
                .setPageSize(PAGED_LIST_PAGE_SIZE)
                .setEnablePlaceholders(IS_PLACEHOLDERS_ENABLED)
                .build()
    }
}