package ru.demin.paging.storage

import io.reactivex.subjects.PublishSubject
import ru.demin.paging.adapter.LoadingState

class NetworkStateSubject {
    private val networkStateSybject = PublishSubject.create<LoadingState>()

    fun subscribe() = networkStateSybject
    fun onNetworkStateChanged(state: LoadingState) = networkStateSybject.onNext(state)
}