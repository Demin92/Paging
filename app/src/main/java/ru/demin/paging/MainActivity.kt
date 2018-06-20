package ru.demin.paging

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import ru.demin.paging.adapter.User
import ru.demin.paging.paging.PageListFactory

class MainActivity : AppCompatActivity() {

    private val pageListFactory = PageListFactory()
    private val userAdapter = GroupAdapter<ViewHolder>()
    private val pagedGroupList = PagedListGroup<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun initUi() {
        user_list.adapter = userAdapter
        userAdapter.add(pagedGroupList)

        pagedGroupList.submitList(pageListFactory.createPageList())
    }
}
