package pl.valueadd.restcountries.presentation.main.countries.details

import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import pl.valueadd.restcountries.domain.manager.CountryDomainManager
import pl.valueadd.restcountries.domain.manager.ExceptionDomainManager
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.utility.reactivex.observeOnMain
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import timber.log.Timber
import javax.inject.Inject

class CountryDetailsPresenter @Inject constructor(
    private val countryManager: CountryDomainManager,
    private val exceptionManager: ExceptionDomainManager
) : BasePresenter<CountryDetailsView>() {

    private var bordersDisposable: Disposable? = null

    override fun attachView(view: CountryDetailsView) {
        super.attachView(view)

        observeCountry(view.countryId)
    }

    fun observeCountry(countryId: String) {
        countryManager
            .observeCountry(countryId)
            .observeOnMain()
            .subscribe(
                ::handleObserveCountrySuccess,
                ::handleObserveCountryFailed
            )
            .addTo(disposables)
    }

    fun observeBorders(borderIds: List<String>): Disposable =
        countryManager
            .observeCountries(borderIds)
            .observeOnMain()
            .subscribe(
                ::handleObserveBordersSuccess,
                ::handleObserveBordersFailed
            )
            .addTo(disposables)

    private fun handleObserveCountrySuccess(model: CountryModel) = onceViewAttached { view->

        Timber.i("Country has been fetched successfully.")

        view.bindModelToView(model)

        view.setBordersCardVisibility(model.borders.isNotEmpty())

        bordersDisposable?.let {
            disposables.remove(it)
        }
        bordersDisposable = observeBorders(model.borders)
    }

    private fun handleObserveCountryFailed(throwable: Throwable) = onceViewAttached {

        Timber.w(throwable, "Country fetch failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }

    private fun handleObserveBordersSuccess(models: List<CountryModel>) = onceViewAttached {

        Timber.i("Borders has been fetched successfully.")

        it.bindBordersToView(models)
    }

    private fun handleObserveBordersFailed(throwable: Throwable) = onceViewAttached {

        Timber.w(throwable, "Borders fetch failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }
}