package pl.valueadd.restcountries.presentation.main.countries.list

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.manager.CountryDomainManager
import pl.valueadd.restcountries.domain.manager.ExceptionDomainManager
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.presentation.main.countries.list.item.ClickCountryItemEventHook
import pl.valueadd.restcountries.presentation.main.countries.list.item.CountryItem
import pl.valueadd.restcountries.utility.reactivex.observeOnMain
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CountryListPresenter
@Inject constructor(
    private val countryManager: CountryDomainManager,
    private val exceptionManager: ExceptionDomainManager
) : BasePresenter<CountryListView>(),
    ClickCountryItemEventHook.Listener {

    companion object {
        private const val DELAY = 350L
    }

    private var countriesDisposable: Disposable? = null

    init {
        onceViewAttached {
            downloadAllCountries()

            observeCountries()
        }
    }

    override fun onCountryItemClick(model: CountryModel) = onceViewAttached {
        it.navigateToCountryDetailsView(model.id)
    }

    fun onSearchQueryChange(query: String) {

        countriesDisposable?.let {
            disposables.remove(it)
        }

        observeCountries(query, true)
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

    private fun observeCountries(query: String = EMPTY, isDelayed: Boolean = false) {
        countriesDisposable = countryManager
            .observeCountries(query)
            .apply {
                if (isDelayed) {
                    delay(DELAY, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                }
            }
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