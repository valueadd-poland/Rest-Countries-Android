package pl.valueadd.restcountries.presentation.main.countries.details

import io.reactivex.disposables.Disposable
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

    fun observeCountry(countryId: String): Disposable =
        countryManager
            .observeCountry(countryId)
            .observeOnMain()
            .subscribe(
                ::handleObserveCountrySuccess,
                ::handleObserveCountryFailed
            )
            .addTo(disposables)

    private fun handleObserveCountrySuccess(model: CountryModel) = onceViewAttached { view ->

        Timber.i("Country has been fetched successfully.")

        // Load the image only when url has been changed to
        // prevent unnecessary reload of the image.
        if (model.flagUrl != this.model?.flagUrl) {
            view.bindFlagToView(model.flagUrl)
        }

        this.model = model
        this.borderModels = model.borders

        view.bindModelToView(model)

        view.setInformationCardVisibility(model.hasBaseInformation)

        view.setBordersCardVisibility(model.borders.isNotEmpty())

        view.bindBordersToView(model.borders)
    }

    private fun handleObserveCountryFailed(throwable: Throwable) = onceViewAttached {

        Timber.w(throwable, "Country fetch failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }

}