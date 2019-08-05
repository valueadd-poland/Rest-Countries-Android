package pl.valueadd.restcountries.presentation.main.countries.list

import android.util.Log
import io.reactivex.rxkotlin.addTo
import pl.valueadd.restcountries.domain.manager.CountryDomainManager
import pl.valueadd.restcountries.domain.manager.ExceptionDomainManager
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.presentation.main.countries.list.item.ClickCountryItemEventHook
import pl.valueadd.restcountries.presentation.main.countries.list.item.CountryItem
import pl.valueadd.restcountries.utility.reactivex.observeOnMain
import javax.inject.Inject

class CountryListPresenter
@Inject constructor(
    private val countryManager: CountryDomainManager,
    private val exceptionManager: ExceptionDomainManager
) : BasePresenter<CountryListView>(),
    ClickCountryItemEventHook.Listener {

    override fun attachView(view: CountryListView) {
        super.attachView(view)

        downloadAllCountries()

        observeAllCountries()
    }

    override fun onCountryItemClick() = onceViewAttached {
        it.navigateToCountryDetailsView()
    }

    private fun downloadAllCountries() {
        countryManager
            .downloadAllCountries()
            .observeOnMain()
            .subscribe(
                ::handleDownloadAllExamplesSuccess,
                ::handleDownloadAllExamplesFailed
            )
            .addTo(disposables)
    }

    private fun handleDownloadAllExamplesSuccess() {

        Log.d(this::class.java.simpleName, "Countries has been downloaded successfully.")
    }

    private fun handleDownloadAllExamplesFailed(throwable: Throwable) = onceViewAttached {

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }

    private fun observeAllCountries() {
        countryManager
            .observeAllCountries()
            .observeOnMain()
            .subscribe(
                ::handleObserveAllExamplesSuccess,
                ::handleObserveAllExamplesFailed
            )
            .addTo(disposables)
    }

    private fun handleObserveAllExamplesSuccess(list: List<CountryModel>) = onceViewAttached { view ->

        val items = list.map { CountryItem(it) }

        view.bindDataToList(items)
    }

    private fun handleObserveAllExamplesFailed(throwable: Throwable) = onceViewAttached {

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }
}