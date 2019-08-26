package pl.valueadd.restcountries.presentation.main.countries.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.transition.Fade
import kotlinx.android.synthetic.main.country_fragment_details.bordersCardView
import kotlinx.android.synthetic.main.country_fragment_details.bordersChipGroup
import kotlinx.android.synthetic.main.country_fragment_details.callingCodesPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.domainsPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.flagImageView
import kotlinx.android.synthetic.main.country_fragment_details.informationCardView
import kotlinx.android.synthetic.main.country_fragment_details.timeZonesPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.toolbar
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.domain.model.country.CountryFlatModel
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.back.BackMVPViewStateFragment
import pl.valueadd.restcountries.utility.common.merge
import pl.valueadd.restcountries.utility.image.loadSVGImage
import pl.valueadd.restcountries.utility.reactivex.onSuccess
import pl.valueadd.restcountries.utility.reactivex.throttleClicks
import pl.valueadd.restcountries.utility.view.getChildAtOrNull
import pl.valueadd.restcountries.utility.view.setVisible
import pl.valueadd.restcountries.view.chip.BorderChip
import javax.inject.Inject

class CountryDetailsFragment : BackMVPViewStateFragment<CountryDetailsView, CountryDetailsPresenter, CountryDetailsViewState>(R.layout.country_fragment_details),
    CountryDetailsView {

    companion object {

        private const val ARG_COUNTRY_ID = "ARG_COUNTRY_ID"

        fun createInstance(countryId: String): CountryDetailsFragment =
            CountryDetailsFragment().also {
                it.arguments = Bundle().apply {
                    putString(ARG_COUNTRY_ID, countryId)
                }
                it.enterTransition = Fade()
                it.exitTransition = Fade()
            }
    }

    @Inject
    override lateinit var mPresenter: CountryDetailsPresenter

    override val toolbarNavigation: Toolbar
        get() = toolbar

    override val countryId: String
        by lazy { arguments?.getString(ARG_COUNTRY_ID) ?: EMPTY }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar()
    }

    override fun createViewState(): CountryDetailsViewState =
        CountryDetailsViewState()

    override fun onSaveViewState() {
        viewState.apply {
            model = presenter.model
            borders = presenter.borderModels
            isBorderCardVisible = bordersCardView.isVisible
            isInformationCardVisible = informationCardView.isVisible
        }
    }

    override fun bindModelToView(model: CountryModel) = model.run {
        setTitle(model.name)
        flagImageView.loadSVGImage(flagUrl)
        callingCodesPropertyView.subtitle = callingCodes.merge()
        domainsPropertyView.subtitle = topLevelDomains.merge()
        timeZonesPropertyView.subtitle = timezones.merge()
    }

    override fun bindBordersToView(models: List<CountryFlatModel>) {
        bordersChipGroup.removeAllViews()

        for (model in models) {
            val chip = createBorderChip(model)

            chip.throttleClicks()
                .onSuccess(disposables, { presenter.onBorderItemClick(model.id) })

            bordersChipGroup.addView(chip)
        }
    }

    override fun setBordersCardVisibility(isVisible: Boolean) =
        bordersCardView.setVisible(isVisible)

    override fun setInformationCardVisibility(isVisible: Boolean) =
        informationCardView.setVisible(isVisible)

    override fun navigateToCountry(countryId: String) {

        val fragment = createInstance(countryId)

        startWithPop(fragment)
    }

    private fun createBorderChip(model: CountryFlatModel): View =
        BorderChip(requireContext()).bindModel(model)

    private fun initializeToolbar() = toolbarNavigation.run {

        inflateMenu(R.menu.main_menu)

        setOnMenuItemClickListener(::onOptionsItemSelected)

        navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_white_24dp)

        // Sets navigation icon's background
        getChildAtOrNull(0)?.let { navigationIconView ->
            navigationIconView.background = ContextCompat.getDrawable(requireContext(), R.drawable.country_background_navigation_icon)
        }
    }
}