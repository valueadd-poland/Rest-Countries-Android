package pl.valueadd.restcountries.presentation.main.countries.details

import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.country_fragment_details.callingCodesPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.domainsPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.flagImageView
import kotlinx.android.synthetic.main.country_fragment_details.timeZonesPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.titleTextView
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.fragment.base.BaseMVPFragment
import pl.valueadd.restcountries.utility.common.merge
import pl.valueadd.restcountries.utility.image.loadSVGImage
import javax.inject.Inject

class CountryDetailsFragment : BaseMVPFragment<CountryDetailsView, CountryDetailsPresenter>(R.layout.country_fragment_details),
    CountryDetailsView {

    companion object {

        private const val ARG_COUNTRY_ID = "ARG_COUNTRY_ID"

        fun createInstance(countryId: String): CountryDetailsFragment =
            CountryDetailsFragment().also {
                it.arguments = Bundle().apply {
                    putString(ARG_COUNTRY_ID, countryId)
                }
            }
    }

    @Inject
    override lateinit var mPresenter: CountryDetailsPresenter

    override val countryId: String
        by lazy { arguments?.getString(ARG_COUNTRY_ID) ?: EMPTY }

    override fun bindModelToView(model: CountryModel) {
        model.apply {
            flagImageView.loadSVGImage(model.flagUrl, R.drawable.ic_language_white_24dp, ContextCompat.getColor(requireContext(), R.color.blackAlpha40))
            titleTextView.text = model.name
            callingCodesPropertyView.subtitle = callingCodes.merge()
            domainsPropertyView.subtitle = topLevelDomains.merge()
            timeZonesPropertyView.subtitle = timezones.merge()
        }
    }
}