package pl.valueadd.restcountries.presentation.main.countries.details

import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.fragment.base.BaseMVPFragment
import javax.inject.Inject

class CountryDetailsFragment : BaseMVPFragment<CountryDetailsView, CountryDetailsPresenter>(R.layout.country_fragment_details),
    CountryDetailsView {

    companion object {

        fun createInstance(): CountryDetailsFragment =
            CountryDetailsFragment()
    }

    @Inject
    override lateinit var mPresenter: CountryDetailsPresenter
}