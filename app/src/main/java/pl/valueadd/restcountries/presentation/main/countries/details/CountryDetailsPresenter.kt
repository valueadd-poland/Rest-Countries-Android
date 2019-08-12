package pl.valueadd.restcountries.presentation.main.countries.details

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

    override fun attachView(view: CountryDetailsView) {
        super.attachView(view)

//        downloadCountry(view.countryId)

        observeCountry(view.countryId)
    }

//    fun downloadCountry(countryId: String) {
//        countryManager
//            .downloadCountry(countryId)
//            .subscribeOnIo()
//            .subscribe(
//                ::handleDownloadCountrySuccess,
//                ::handleDownloadCountryFailed
//            )
//            .addTo(disposables)
//    }

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

    private fun handleObserveCountrySuccess(model: CountryModel) = onceViewAttached {

        Timber.i("Country has been fetched successfully.")

        it.bindModelToView(model)
    }

    private fun handleObserveCountryFailed(throwable: Throwable) = onceViewAttached {

        Timber.w(throwable, "Country fetch failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }

    private fun handleDownloadCountrySuccess() {

        Timber.i("Country has been downloaded successfully.")

    }

    private fun handleDownloadCountryFailed(throwable: Throwable) = onceViewAttached {

        Timber.w(throwable, "Country download failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }
}