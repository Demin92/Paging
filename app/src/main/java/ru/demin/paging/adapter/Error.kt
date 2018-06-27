package ru.demin.paging.adapter

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_error.view.*
import ru.demin.paging.R

class Error(private val retry: () -> Unit) : Item<ViewHolder>() {
    override fun getLayout() = R.layout.item_error

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.item_error_retry.setOnClickListener { retry.invoke() }
    }
}