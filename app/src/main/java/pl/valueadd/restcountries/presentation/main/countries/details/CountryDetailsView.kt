package pl.valueadd.restcountries.presentation.main.countries.details

import pl.valueadd.restcountries.domain.model.country.CountryFlatModel
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.BaseView

interface CountryDetailsView : BaseView {

    val countryId: String

    fun bindModelToView(model: CountryModel)

    fun bindBordersToView(models: List<CountryFlatModel>)

    /**
     * Binds image to the view.
     *
     * Should be called separately to prevent reload effect on image view.
     */
    fun bindFlagToView(flagUrl: String)

    fun setBordersCardVisibility(isVisible: Boolean)

    fun setInformationCardVisibility(isVisible: Boolean)

    fun navigateToCountry(countryId: String)

}