package pl.valueadd.restcountries.presentation.main.countries.list

import com.mikepenz.fastadapter.IItem
import pl.valueadd.restcountries.presentation.base.BaseView

interface CountryListView : BaseView {

    fun bindDataToList(list: List<IItem<*>>)

    fun navigateToCountryDetailsView(countryId: String)
}