package ru.demin.paging.storage

import io.reactivex.subjects.PublishSubject

class RetrySubject {

    private val retrySubject = PublishSubject.create<() -> Unit>()

    fun subscribe() = retrySubject

    fun onRetryStateChanged(retry: () -> Unit) = retrySubject.onNext(retry)
}