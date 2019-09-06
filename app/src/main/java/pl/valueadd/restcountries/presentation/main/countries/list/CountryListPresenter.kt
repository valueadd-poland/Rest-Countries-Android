package pl.valueadd.restcountries.presentation.main.countries.list

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.manager.CountryDomainManager
import pl.valueadd.restcountries.domain.manager.ExceptionDomainManager
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.domain.model.helper.Filter
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.presentation.main.countries.list.item.ClickCountryItemEventHook
import pl.valueadd.restcountries.presentation.main.countries.list.item.CountryItem
import timber.log.Timber
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

    init {
        onceViewAttached {
            downloadAllCountries()

            observeCountries()
        }
    }

    var isAscending: Boolean = true

    private var observeCountriesJob: Job? = null

    override fun onCountryItemClick(model: CountryModel) = onceViewAttached {
        it.navigateToCountryDetailsView(model.id)
    }

    fun onSortByNameViewClick() = onceViewAttached {
        it.showSortByNameDialog()
    }

    fun onSortByNameChanged(isAscending: Boolean) {
        this.isAscending = isAscending
        onQueryChanged()
    }

    fun onQueryChanged(query: String) {
        val filter = if (isAscending) {
            Filters.NameAsc
        } else {
            Filters.NameDsc
        }

        observeCountriesJob?.cancel()

        observeCountries(query, true, filter)
    }

    private fun onQueryChanged() = onceViewAttached {
        onQueryChanged(it.searchQuery)
    }

    private fun downloadAllCountries() {
        scope.launch(Dispatchers.Default + exceptionHandler(::onDownloadAllExamplesFailed)) {
            countryManager.downloadAllCountries()
            launch(Dispatchers.Main) {
                onDownloadAllExamplesSuccess()
            }
        }
    }

    private fun onDownloadAllExamplesSuccess() {

        Timber.d("Countries has been downloaded successfully.")
    }

    private fun onDownloadAllExamplesFailed(throwable: Throwable) = onceViewAttached {

        Timber.e(throwable, "Download all countries failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }

    private fun observeCountries(query: String = EMPTY, isDelayed: Boolean = false, filter: Filter<CountryModel> = Filters.NameAsc) {
        observeCountriesJob = scope.launch(exceptionHandler(::onObserveAllCountriesFailed)) {
            var countriesFlow = countryManager.observeCountries(query, filter)
            if (isDelayed) {
                countriesFlow = countriesFlow.debounce(DELAY)
            }
            countriesFlow.collectLatest {
                    onObserveAllCountriesSuccess(it)
            }
        }
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

    internal class Filters {

        object NameAsc : Filter<CountryModel>(true)

        object NameDsc : Filter<CountryModel>(false)
    }
}