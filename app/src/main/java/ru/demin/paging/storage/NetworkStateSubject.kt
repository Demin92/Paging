package ru.demin.paging.storage

import io.reactivex.subjects.PublishSubject
import ru.demin.paging.adapter.LoadingState

class NetworkStateSubject {
    private val networkStateSubject = PublishSubject.create<LoadingState>()

    fun subscribe() = networkStateSubject

    fun onNetworkStateChanged(state: LoadingState) = networkStateSubject.onNext(state)
}