package ru.demin.paging.storage

import android.content.Context
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.demin.paging.adapter.Admin
import ru.demin.paging.adapter.User
import ru.demin.paging.utils.NetworkUtils
import java.net.ConnectException

class UserStorage(private val context: Context) {
    fun loadItems(startPosition: Int, loadSize: Int): Single<List<Item<ViewHolder>>> {
        val single = Single.just(generateUsers(startPosition, loadSize).toList()).map {
            if (!NetworkUtils.isOnline(context)) {
                throw ConnectException()
            }
            it
        }
        return single.apply { subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }

    private fun generateUsers(startPosition: Int, loadSize: Int): ArrayList<Item<ViewHolder>> {
        val users = arrayListOf<Item<ViewHolder>>()
        if (startPosition > 0) {
            Thread.sleep(2000)
        }


        for (i in startPosition..(startPosition + loadSize - 1)) {
            if ((Math.random() * 5) < 2) {
                users.add(Admin("admin $i"))
            } else {
                users.add(User("user $i"))
            }
        }
        return users
    }
}