package ru.demin.paging.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_user.view.*
import ru.demin.paging.R

class UserViewHolder(private val eventListener: UserItemEventListener, parent: ViewGroup):
        RecyclerView.ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
{
    fun bind(item: User?) {
        itemView.item_user_name.text = item?.name?:"null"
        itemView.item_user_name.setOnClickListener {
            item?.let {
                eventListener.onUserDelete(it)
            }
        }
    }

    interface UserItemEventListener {
        fun onUserDelete(user: User)
    }
}