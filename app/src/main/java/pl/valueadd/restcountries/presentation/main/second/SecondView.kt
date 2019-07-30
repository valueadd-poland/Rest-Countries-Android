package pl.valueadd.restcountries.presentation.main.second

import com.mikepenz.fastadapter.IItem

interface SecondView : pl.valueadd.restcountries.presentation.base.BaseView {

    fun bindDataToList(list: List<IItem<*>>)
}