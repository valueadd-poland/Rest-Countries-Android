package pl.valueadd.restcountries.presentation.main.root

import javax.inject.Inject

class RootPresenter @Inject constructor() : pl.valueadd.restcountries.presentation.base.BasePresenter<RootView>() {

    fun onAccountButtonClick() = ifViewAttached {
        it.navigateToAccountView()
    }
}