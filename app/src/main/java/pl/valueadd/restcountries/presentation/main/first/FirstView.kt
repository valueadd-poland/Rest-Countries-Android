package pl.valueadd.restcountries.presentation.main.first

interface FirstView : pl.valueadd.restcountries.presentation.base.BaseView {

    fun navigateToAboutView()

    fun bindCountToView(count: Int)
}