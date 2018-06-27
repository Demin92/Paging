package ru.demin.paging.storage

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import ru.demin.paging.adapter.Admin
import ru.demin.paging.adapter.User

class UserStorage {
    fun loadItems(startPosition: Int, loadSize: Int): List<Item<ViewHolder>> {
        val users = arrayListOf<Item<ViewHolder>>()
        if (startPosition > 0) {
            Thread.sleep(2000)
        }
        for (i in startPosition..(startPosition + loadSize - 1)) {
            if ( (Math.random() * 5) < 2 ) {
                users.add(Admin("admin $i"))
            } else {
                users.add(User("user $i"))
            }
        }
        return users
    }
}