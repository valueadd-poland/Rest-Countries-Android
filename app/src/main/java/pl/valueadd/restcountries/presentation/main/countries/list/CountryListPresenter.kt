package pl.valueadd.restcountries.presentation.main.countries.list

import io.reactivex.rxkotlin.addTo
import pl.valueadd.restcountries.domain.manager.CountryDomainManager
import pl.valueadd.restcountries.domain.manager.ExceptionDomainManager
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.presentation.main.countries.list.item.ClickCountryItemEventHook
import pl.valueadd.restcountries.presentation.main.countries.list.item.CountryItem
import pl.valueadd.restcountries.utility.reactivex.observeOnMain
import timber.log.Timber
import javax.inject.Inject

class CountryListPresenter
@Inject constructor(
    private val countryManager: CountryDomainManager,
    private val exceptionManager: ExceptionDomainManager
) : BasePresenter<CountryListView>(),
    ClickCountryItemEventHook.Listener {

    init {
        onceViewAttached {
            downloadAllCountries()

            observeAllCountries()
        }
    }

    override fun onCountryItemClick(model: CountryModel) = onceViewAttached {
        it.navigateToCountryDetailsView(model.id)
    }

    private fun downloadAllCountries() {
        countryManager
            .downloadAllCountries()
            .observeOnMain()
            .subscribe(
                ::onDownloadAllExamplesSuccess,
                ::onDownloadAllExamplesFailed
            )
            .addTo(disposables)
    }

    private fun onDownloadAllExamplesSuccess() {

        Timber.d("Countries has been downloaded successfully.")
    }

    private fun onDownloadAllExamplesFailed(throwable: Throwable) = onceViewAttached {

        Timber.e(throwable, "Download all countries failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }

    private fun observeAllCountries() {
        countryManager
            .observeAllCountries()
            .map { list ->
                list.sortedBy { it.name }
            }
            .observeOnMain()
            .subscribe(
                ::onObserveAllCountriesSuccess,
                ::onObserveAllCountriesFailed
            )
            .addTo(disposables)
    }

    private fun onObserveAllCountriesSuccess(list: List<CountryModel>) = onceViewAttached { view ->

        val items = list.map { CountryItem(it) }

        view.bindDataToList(items)
    }

    private fun onObserveAllCountriesFailed(throwable: Throwable) = onceViewAttached {

        Timber.e(throwable, "Observe all countries failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }
}