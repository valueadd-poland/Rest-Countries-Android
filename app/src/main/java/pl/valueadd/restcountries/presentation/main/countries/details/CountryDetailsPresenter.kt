package pl.valueadd.restcountries.presentation.main.countries.details

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.valueadd.restcountries.domain.manager.CountryDomainManager
import pl.valueadd.restcountries.domain.manager.ExceptionDomainManager
import pl.valueadd.restcountries.domain.model.country.CountryFlatModel
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class CountryDetailsPresenter @Inject constructor(
    private val countryManager: CountryDomainManager,
    private val exceptionManager: ExceptionDomainManager
) : BasePresenter<CountryDetailsView>() {

    var model: CountryModel? = null
        private set

    var borderModels: List<CountryFlatModel> = emptyList()
        private set

    init {
        onceViewAttached {
            observeCountry(it.countryId)
        }
    }

    fun onBorderItemClick(countryId: String) = onceViewAttached {
        it.navigateToCountry(countryId)
    }

    private fun observeCountry(countryId: String) {
        scope.launch(exceptionHandler(::onObserveCountryFailed)) {
            countryManager
                .observeCountry(countryId)
                .collectLatest {
                    onObserveCountrySuccess(it)
                }
        }
    }

    private fun onObserveCountrySuccess(model: CountryModel) = onceViewAttached { view ->

        Timber.i("Country has been fetched successfully.")

        this.model = model
        this.borderModels = model.borders

        view.bindModelToView(model)

        view.setInformationCardVisibility(model.hasBaseInformation)

        view.setBordersCardVisibility(model.borders.isNotEmpty())

        view.bindBordersToView(model.borders)
    }

    private fun onObserveCountryFailed(throwable: Throwable) = onceViewAttached {

        Timber.w(throwable, "Country fetch failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }
}