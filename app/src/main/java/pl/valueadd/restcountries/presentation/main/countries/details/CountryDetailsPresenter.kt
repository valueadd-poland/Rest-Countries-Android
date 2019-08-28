package pl.valueadd.restcountries.presentation.main.countries.details

import io.reactivex.rxkotlin.addTo
import pl.valueadd.restcountries.domain.manager.CountryDomainManager
import pl.valueadd.restcountries.domain.manager.ExceptionDomainManager
import pl.valueadd.restcountries.domain.model.country.CountryFlatModel
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.utility.reactivex.observeOnMain
import timber.log.Timber
import javax.inject.Inject

class CountryDetailsPresenter @Inject constructor(
    private val countryManager: CountryDomainManager,
    private val exceptionManager: ExceptionDomainManager
) : BasePresenter<CountryDetailsView>() {

    var countryModel: CountryModel? = null
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

    fun onMapReady() {
        val model = countryModel ?: return

        onceViewAttached {
            it.bindPositionDataToMapView(model)
        }
    }

    private fun observeCountry(countryId: String) {
        countryManager
            .observeCountry(countryId)
            .observeOnMain()
            .subscribe(
                ::onObserveCountrySuccess,
                ::onObserveCountryFailed
            )
            .addTo(disposables)
    }

    private fun onObserveCountrySuccess(model: CountryModel) = onceViewAttached { view ->

        Timber.i("Country has been fetched successfully.")

        this.countryModel = model
        this.borderModels = model.borders

        view.bindModelToView(model)

        view.setInformationCardVisibility(model.hasBaseInformation)

        view.setBordersCardVisibility(model.borders.isNotEmpty())

        view.setMapCardVisibility(model.latLng.hasNotDefaultValues)

        view.bindBordersToView(model.borders)

        view.bindPositionDataToMapView(model)
    }

    private fun onObserveCountryFailed(throwable: Throwable) = onceViewAttached {

        Timber.w(throwable, "Country fetch failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }
}