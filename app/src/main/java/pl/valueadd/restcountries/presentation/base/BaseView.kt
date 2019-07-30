package pl.valueadd.restcountries.presentation.base

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseView : MvpView {

    fun showError(message: String)
}