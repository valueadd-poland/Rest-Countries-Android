package pl.valueadd.restcountries.presentation.base.fragment.viewstate

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState
import pl.valueadd.restcountries.presentation.base.BaseView

abstract class BaseViewState<V : BaseView> : RestorableViewState<V>