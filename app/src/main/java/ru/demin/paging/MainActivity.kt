package ru.demin.paging

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.demin.paging.adapter.Error
import ru.demin.paging.adapter.Loading
import ru.demin.paging.adapter.LoadingState
import ru.demin.paging.paging.PageListFactory
import ru.demin.paging.storage.NetworkStateSubject
import ru.demin.paging.storage.RetrySubject

class MainActivity : AppCompatActivity() {

    private val pageListFactory = PageListFactory()
    private val userAdapter = GroupAdapter<ViewHolder>()
    private val pagedGroupList = PagedListGroup<Item<ViewHolder>>()

    private val loadingItem = Loading()
    private var errorItem: Error? = null

    private val networkStateSubject = NetworkStateSubject()
    private val retrySubject = RetrySubject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        networkStateSubject.subscribe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    handleLoadingState(it)
                }
        retrySubject.subscribe()
                .subscribe {
                    errorItem = Error { it.invoke() }
                }

    }

    private fun initUi() {
        user_list.adapter = userAdapter
        userAdapter.add(pagedGroupList)

        pagedGroupList.submitList(pageListFactory.createPageList(networkStateSubject, retrySubject, this))
    }

    private fun handleLoadingState(state: LoadingState) {
        removePreviuos()
        when (state) {
            LoadingState.LOADING -> {
                userAdapter.add(loadingItem)
            }
            LoadingState.LOADED -> {
            }
            LoadingState.ERROR -> {
                userAdapter.add(errorItem!!)
            }
        }
    }

    private fun removePreviuos() {
        removeLoading()
        removeError()
    }

    private fun removeError() {
        errorItem?.let {
            if (userAdapter.getAdapterPosition(it) >= 0) {
                userAdapter.remove(it)
            }
        }
    }

    private fun removeLoading() {
        if (userAdapter.getAdapterPosition(loadingItem) >= 0) {
            userAdapter.remove(loadingItem)
        }
    }
}
