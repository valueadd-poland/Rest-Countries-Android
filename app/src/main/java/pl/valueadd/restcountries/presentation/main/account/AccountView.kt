package pl.valueadd.restcountries.presentation.main.account

import pl.valueadd.restcountries.domain.model.AccountModel

interface AccountView : pl.valueadd.restcountries.presentation.base.BaseView {

    fun bindAccountDataToView(model: AccountModel)

    fun retrieveFirstName(): String

    fun retrieveSurname(): String

    fun retrieveEmail(): String
}