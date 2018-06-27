package ru.demin.paging.adapter

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_user.view.*
import ru.demin.paging.R

class Loading : Item<ViewHolder>() {
    override fun getLayout() = R.layout.item_loading

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }
}