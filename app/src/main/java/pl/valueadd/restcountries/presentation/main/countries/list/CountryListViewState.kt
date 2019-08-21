package pl.valueadd.restcountries.presentation.main.countries.list

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.BaseViewState

class CountryListViewState : BaseViewState<CountryListView>() {

    companion object {
        private const val KEY_IS_ASCENDING = "CountryListViewState-isSortByNameDisplaying"
    }

    var isSortByNameDialogDisplaying: Boolean = false

    override fun saveInstanceState(out: Bundle) {
        out.apply {
            putBoolean(KEY_IS_ASCENDING, isSortByNameDialogDisplaying)
        }
    }

    override fun restoreInstanceState(bundleIn: Bundle?): RestorableViewState<CountryListView> {
        bundleIn?.apply {
            isSortByNameDialogDisplaying = getBoolean(KEY_IS_ASCENDING)
        }

        return this
    }

    override fun apply(view: CountryListView?, retained: Boolean) {
        view?.run {
            if (isSortByNameDialogDisplaying) {
                view.showSortByNameDialog()
            }
        }
    }
}