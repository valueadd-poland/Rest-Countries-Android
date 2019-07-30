package pl.valueadd.restcountries.presentation.main.account

import android.os.Bundle
import android.os.Parcelable
import pl.valueadd.restcountries.domain.model.AccountModel
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.BaseViewState
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState

class AccountViewState : BaseViewState<AccountView>() {

    companion object {
        private const val KEY_DATA = "AccountViewState-model"
    }

    var model: Parcelable = AccountModel()

    override fun saveInstanceState(out: Bundle) {
        out.putParcelable(KEY_DATA, model)
    }

    override fun restoreInstanceState(bundleIn: Bundle?): RestorableViewState<AccountView> {
        bundleIn?.let {
            model = it.getParcelable(KEY_DATA)
        }

        return this
    }

    override fun apply(view: AccountView?, retained: Boolean) {
        view?.bindAccountDataToView(model as AccountModel)
    }
}