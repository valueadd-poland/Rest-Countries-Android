package pl.valueadd.restcountries.presentation.main.third

import javax.inject.Inject

class ThirdPresenter @Inject constructor() : pl.valueadd.restcountries.presentation.base.BasePresenter<ThirdView>() {

    fun onAboutButtonClick() = ifViewAttached {
        it.navigateToAboutView()
    }
}