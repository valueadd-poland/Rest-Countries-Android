package pl.valueadd.restcountries.presentation.main.countries.details

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
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
) : BasePresenter<CountryDetailsView>(),
    OnMapReadyCallback {

    var model: CountryModel? = null
        private set

    var borderModels: List<CountryFlatModel> = emptyList()
        private set

    private var map: GoogleMap? = null

    init {
        onceViewAttached {
            observeCountry(it.countryId)
        }
    }

    fun onBorderItemClick(countryId: String) = onceViewAttached {
        it.navigateToCountry(countryId)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap.also { map ->
            val model = model ?: return@also

            onceViewAttached {
                it.bindPositionDataToMapView(map, model.latLng, model.name)
            }
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

        this.model = model
        this.borderModels = model.borders

        view.bindModelToView(model)

        view.setInformationCardVisibility(model.hasBaseInformation)

        view.setBordersCardVisibility(model.borders.isNotEmpty())

        view.setMapCardVisibility(model.latLng.hasNotDefaultValues)

        view.bindBordersToView(model.borders)

        view.bindPositionDataToMapView(map, model.latLng, model.name)
    }

    private fun onObserveCountryFailed(throwable: Throwable) = onceViewAttached {

        Timber.w(throwable, "Country fetch failed.")

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }
}