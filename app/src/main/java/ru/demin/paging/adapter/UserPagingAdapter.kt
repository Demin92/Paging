package ru.demin.paging.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup

class UserPagingAdapter : PagedListAdapter<User, UserViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User?, newItem: User?): Boolean {
                return oldItem?.name == newItem?.name
            }

            override fun areContentsTheSame(oldItem: User?, newItem: User?): Boolean {
                return oldItem?.name == newItem?.name
            }
        }
    }
}