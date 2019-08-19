package pl.valueadd.restcountries.presentation.base

import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : BaseView> : MvpQueuingBasePresenter<V>() {

    /**
     * Contains disposable subscription of streams.
     */
    protected val disposables: CompositeDisposable
        by lazy { CompositeDisposable() }

    /**
     * Add subscription to composite.
     */
    protected fun addDisposable(disposable: Disposable): Boolean =
        disposables.add(disposable)

    /**
     * Clear all subscriptions.
     */
    fun clearDisposables() {
        disposables.clear()
    }
}