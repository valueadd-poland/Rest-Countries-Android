package pl.valueadd.restcountries.presentation.main.countries.details

import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.BaseView

interface CountryDetailsView : BaseView {

    val countryId: String

    fun bindModelToView(model: CountryModel)

}