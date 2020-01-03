package pl.valueadd.restcountries.presentation.main.countries.details

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState
import pl.valueadd.restcountries.domain.model.country.CountryFlatModel
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.BaseViewState

class CountryDetailsViewState : BaseViewState<CountryDetailsView>() {

    companion object {
        private const val KEY_BORDERS = "CountryDetailsViewState-borders"
        private const val KEY_MODEL = "CountryDetailsViewState-model"
        private const val KEY_IS_BORDER_CARD_VISIBLE = "CountryDetailsViewState-isBorderCardVisible"
        private const val KEY_IS_INFORMATION_CARD_VISIBLE = "CountryDetailsViewState-isInformationCardVisible"
        private const val KEY_IS_MAP_CARD_VISIBLE = "CountryDetailsViewState-isMapCardVisible"
    }

    var borders: List<CountryFlatModel> = emptyList()
    var model: CountryModel? = null
    var isBorderCardVisible: Boolean = false
    var isInformationCardVisible: Boolean = false
    var isMapCardVisible: Boolean = false

    override fun saveInstanceState(out: Bundle) = out.run {
        putParcelableArrayList(KEY_BORDERS, ArrayList(borders))
        putParcelable(KEY_MODEL, model)
        putBoolean(KEY_IS_BORDER_CARD_VISIBLE, isBorderCardVisible)
        putBoolean(KEY_IS_INFORMATION_CARD_VISIBLE, isInformationCardVisible)
        putBoolean(KEY_IS_MAP_CARD_VISIBLE, isMapCardVisible)
    }

    override fun restoreInstanceState(bundleIn: Bundle?): RestorableViewState<CountryDetailsView> {
        bundleIn?.let {
            isBorderCardVisible = bundleIn.getBoolean(KEY_IS_BORDER_CARD_VISIBLE)
            isInformationCardVisible = bundleIn.getBoolean(KEY_IS_INFORMATION_CARD_VISIBLE)
            isMapCardVisible = bundleIn.getBoolean(KEY_IS_MAP_CARD_VISIBLE)
            model = bundleIn.getParcelable(KEY_MODEL)
            borders = bundleIn.getParcelableArrayList(KEY_BORDERS) ?: emptyList()
        }

        return this
    }

    override fun apply(view: CountryDetailsView?, retained: Boolean) {
        view?.run {
            model?.let {
                bindModelToView(it)
            }
            bindBordersToView(borders)
            setBordersCardVisibility(isBorderCardVisible)
            setInformationCardVisibility(isInformationCardVisible)
            setMapCardVisibility(isMapCardVisible)
        }
    }
}