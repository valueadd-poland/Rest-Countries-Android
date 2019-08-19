package pl.valueadd.restcountries.presentation.main.countries.details

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.transition.Fade
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.country_fragment_details.bordersCardView
import kotlinx.android.synthetic.main.country_fragment_details.bordersChipGroup
import kotlinx.android.synthetic.main.country_fragment_details.callingCodesPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.domainsPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.flagImageView
import kotlinx.android.synthetic.main.country_fragment_details.informationCardView
import kotlinx.android.synthetic.main.country_fragment_details.timeZonesPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.titleTextView
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.domain.model.country.CountryFlatModel
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.base.BaseMVPViewStateFragment
import pl.valueadd.restcountries.utility.common.merge
import pl.valueadd.restcountries.utility.image.Options
import pl.valueadd.restcountries.utility.image.listener.SvgSoftwareLayerSetter
import pl.valueadd.restcountries.utility.image.loadSVGImage
import pl.valueadd.restcountries.utility.image.target.ChipTarget
import pl.valueadd.restcountries.utility.reactivex.onSuccess
import pl.valueadd.restcountries.utility.reactivex.throttleClicks
import pl.valueadd.restcountries.utility.view.setVisible
import javax.inject.Inject

class CountryDetailsFragment : BaseMVPViewStateFragment<CountryDetailsView, CountryDetailsPresenter, CountryDetailsViewState>(R.layout.country_fragment_details),
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

    override val countryId: String
        by lazy { arguments?.getString(ARG_COUNTRY_ID) ?: EMPTY }

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

    override fun bindModelToView(model: CountryModel) {
        model.apply {
            titleTextView.text = name
            callingCodesPropertyView.subtitle = callingCodes.merge()
            domainsPropertyView.subtitle = topLevelDomains.merge()
            timeZonesPropertyView.subtitle = timezones.merge()
        }
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

    override fun bindFlagToView(flagUrl: String) =
        flagImageView.loadSVGImage(flagUrl)

    override fun setBordersCardVisibility(isVisible: Boolean) =
        bordersCardView.setVisible(isVisible)

    override fun setInformationCardVisibility(isVisible: Boolean) =
        informationCardView.setVisible(isVisible)

    override fun navigateToCountry(countryId: String) {

        val fragment = createInstance(countryId)

        startWithPop(fragment)
    }

    private fun createBorderChip(model: CountryFlatModel): View =
        Chip(requireContext()).apply {
            isClickable = true
            text = model.name
            Glide.with(this)
                .`as`(PictureDrawable::class.java)
                .apply(Options.svgRequest)
                .listener(SvgSoftwareLayerSetter())
                .load(model.flagUrl)
                .into(ChipTarget(this))
        }
}