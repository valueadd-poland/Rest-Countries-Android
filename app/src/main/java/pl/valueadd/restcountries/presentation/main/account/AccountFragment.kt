package pl.valueadd.restcountries.presentation.main.account

import kotlinx.android.synthetic.main.fragment_account.emailText
import kotlinx.android.synthetic.main.fragment_account.firstNameText
import kotlinx.android.synthetic.main.fragment_account.surnameText
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.domain.model.AccountModel
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.back.BackMVPViewStateFragment
import javax.inject.Inject

class AccountFragment :
    BackMVPViewStateFragment<AccountView, AccountPresenter, AccountViewState>(R.layout.fragment_account),
    AccountView {

    companion object {

        fun createInstance(): AccountFragment =
            AccountFragment()
    }

    @Inject
    override lateinit var mPresenter: AccountPresenter

    override val titleRes: Int =
        R.string.account_title

    override fun onSaveViewState() {
        viewState.model = AccountModel(
            retrieveFirstName(),
            retrieveSurname(),
            retrieveEmail()
        )
    }

    override fun createViewState(): AccountViewState =
        AccountViewState()

    override fun bindAccountDataToView(model: AccountModel) {
        firstNameText.setText(model.firstName)
        surnameText.setText(model.surname)
        emailText.setText(model.email)
    }

    override fun retrieveFirstName(): String =
        firstNameText.text.toString()

    override fun retrieveSurname(): String =
        surnameText.text.toString()

    override fun retrieveEmail(): String =
        emailText.text.toString()
}