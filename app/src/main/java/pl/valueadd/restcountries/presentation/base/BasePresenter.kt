package pl.valueadd.restcountries.presentation.base

import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.MainScope
import pl.valueadd.restcountries.utility.coroutines.cancelSafe

abstract class BasePresenter<V : BaseView> : MvpQueuingBasePresenter<V>() {

    protected val scope by lazy { MainScope() }

    fun exceptionHandler(method: (Throwable) -> Unit): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable -> method(throwable) }
    }

    override fun detachView() {
        scope.cancelSafe()
        super.detachView()
    }
}