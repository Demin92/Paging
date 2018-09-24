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
    companion object {
        private const val FAKE_REQUEST_DURATION = 2000L
        private const val USER_RANDROMIZER_SIZE = 5
        private const val USER_RANDROMIZER_ADMIN_BOUND = 2
    }


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
            Thread.sleep(FAKE_REQUEST_DURATION)
        }


        for (i in startPosition..(startPosition + loadSize - 1)) {
            if ((Math.random() * USER_RANDROMIZER_SIZE) < USER_RANDROMIZER_ADMIN_BOUND) {
                users.add(Admin("admin $i"))
            } else {
                users.add(User("user $i"))
            }
        }
        return users
    }
}