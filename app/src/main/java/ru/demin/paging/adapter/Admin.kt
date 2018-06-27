package ru.demin.paging.adapter

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_admin.view.*
import kotlinx.android.synthetic.main.item_user.view.*
import ru.demin.paging.R

class Admin(val name: String) : Item<ViewHolder>() {
    override fun getLayout() = R.layout.item_admin

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.item_admin_name.text = name
    }
}