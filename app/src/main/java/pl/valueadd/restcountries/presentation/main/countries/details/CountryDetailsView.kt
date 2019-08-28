package pl.valueadd.restcountries.presentation.main.countries.details

import pl.valueadd.restcountries.domain.model.country.CountryFlatModel
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.BaseView

interface CountryDetailsView : BaseView {

    val countryId: String

    fun bindModelToView(model: CountryModel)

    fun bindBordersToView(models: List<CountryFlatModel>)

    fun setBordersCardVisibility(isVisible: Boolean)

    fun setInformationCardVisibility(isVisible: Boolean)

    fun setMapCardVisibility(isVisible: Boolean)

    fun navigateToCountry(countryId: String)

    fun bindPositionDataToMapView(model: CountryModel)
}