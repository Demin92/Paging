package ru.demin.paging.paging

import android.arch.paging.AsyncPagedListDiffer
import android.arch.paging.PagedList
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import com.xwray.groupie.*
import ru.demin.paging.adapter.NullableItem

class PagedListGroup<T : Item<ViewHolder>> : Group, GroupDataObserver {

    private var groupDataObserver: GroupDataObserver? = null

    private val listUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
            groupDataObserver?.onItemRangeInserted(this@PagedListGroup, position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            groupDataObserver?.onItemRangeRemoved(this@PagedListGroup, position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            groupDataObserver?.onItemMoved(this@PagedListGroup, fromPosition, toPosition)
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            groupDataObserver?.onItemRangeChanged(this@PagedListGroup, position, count)
        }
    }

    private val differ = AsyncPagedListDiffer<T>(
            listUpdateCallback,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<T>() {
                override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                    return newItem.isSameAs(oldItem)
                }

                override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                    return newItem == oldItem
                }
            }).build()
    )

    override fun getItemCount() = differ.itemCount

    override fun unregisterGroupDataObserver(groupDataObserver: GroupDataObserver) {
        this.groupDataObserver = null
    }

    override fun getItem(position: Int): Item<ViewHolder> {
        return differ.getItem(position) ?: NullableItem()
    }

    override fun getPosition(item: Item<ViewHolder>): Int {
        val currentList = differ.currentList ?: return -1
        return currentList.indexOf(item)
    }

    override fun registerGroupDataObserver(groupDataObserver: GroupDataObserver) {
        this.groupDataObserver = groupDataObserver
    }

    override fun onChanged(group: Group) {
        groupDataObserver?.onChanged(this)
    }

    override fun onItemRangeRemoved(group: Group, positionStart: Int, itemCount: Int) {
        groupDataObserver?.onItemRangeRemoved(this, positionStart, itemCount)
    }

    override fun onItemInserted(group: Group, position: Int) {
        groupDataObserver?.onItemInserted(this, position)
    }

    override fun onItemRemoved(group: Group, position: Int) {
        groupDataObserver?.onItemRemoved(this, position)
    }

    override fun onItemChanged(group: Group, position: Int) {
        groupDataObserver?.onItemChanged(this, position)
    }

    override fun onItemChanged(group: Group, position: Int, payload: Any?) {
        groupDataObserver?.onItemChanged(this, position, payload)
    }

    override fun onItemRangeInserted(group: Group, positionStart: Int, itemCount: Int) {
        groupDataObserver?.onItemRangeInserted(this, positionStart, itemCount)
    }

    override fun onItemMoved(group: Group, fromPosition: Int, toPosition: Int) {
        groupDataObserver?.onItemMoved(this, fromPosition, toPosition)
    }

    override fun onItemRangeChanged(group: Group, positionStart: Int, itemCount: Int) {
        groupDataObserver?.onItemRangeChanged(this, positionStart, itemCount)
    }

    override fun onItemRangeChanged(group: Group, positionStart: Int, itemCount: Int, payload: Any?) {
        groupDataObserver?.onItemRangeChanged(this, positionStart, itemCount, payload)
    }

    fun submitList(pagedList: PagedList<T>) {
        differ.submitList(pagedList)
    }
}