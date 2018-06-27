package ru.demin.paging.storage

import io.reactivex.subjects.PublishSubject
import ru.demin.paging.adapter.LoadingState

class RetrySubject {
    private val retrySubject = PublishSubject.create<()->Unit>()

    fun subscribe() = retrySubject
    fun onNetworkStateChanged(retry: ()->Unit) = retrySubject.onNext(retry)
}