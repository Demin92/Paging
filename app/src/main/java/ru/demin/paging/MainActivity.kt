package ru.demin.paging

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import ru.demin.paging.adapter.User
import ru.demin.paging.adapter.UserPagingAdapter
import ru.demin.paging.adapter.UserViewHolder
import ru.demin.paging.paging.PageListFactory
import ru.demin.paging.paging.UserDataSource
import ru.demin.paging.storage.UserStorage

class MainActivity : AppCompatActivity(), UserViewHolder.UserItemEventListener {

    private val userStorage = UserStorage()
    private val pageListFactory = PageListFactory(userStorage)
    private lateinit var userAdapter : UserPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    override fun onUserDelete(user: User) {
        userStorage.deleteUser(user)
    }

    private fun initUi() {
        userAdapter = UserPagingAdapter(this)
        user_list.adapter = userAdapter
        pageListFactory.createPageList().subscribe {
            Log.d(UserDataSource.LOG_TAG, "submit")
            userAdapter.submitList(it)
        }
    }
}
