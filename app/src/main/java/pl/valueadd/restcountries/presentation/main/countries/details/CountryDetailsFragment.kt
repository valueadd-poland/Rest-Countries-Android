package pl.valueadd.restcountries.presentation.main.countries.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.transition.Fade
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.country_fragment_details.appBarLayout
import kotlinx.android.synthetic.main.country_fragment_details.bordersCardView
import kotlinx.android.synthetic.main.country_fragment_details.bordersChipGroup
import kotlinx.android.synthetic.main.country_fragment_details.callingCodesPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.domainsPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.flagImageView
import kotlinx.android.synthetic.main.country_fragment_details.googleMapView
import kotlinx.android.synthetic.main.country_fragment_details.informationCardView
import kotlinx.android.synthetic.main.country_fragment_details.mapCardView
import kotlinx.android.synthetic.main.country_fragment_details.timeZonesPropertyView
import kotlinx.android.synthetic.main.country_fragment_details.toolbar
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO
import pl.valueadd.restcountries.BuildConfig
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.domain.model.country.CountryFlatModel
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.domain.model.country.LatLngModel
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.back.BackMVPViewStateFragment
import pl.valueadd.restcountries.utility.common.merge
import pl.valueadd.restcountries.utility.image.loadSVGImage
import pl.valueadd.restcountries.utility.reactivex.onSuccess
import pl.valueadd.restcountries.utility.reactivex.throttleClicks
import pl.valueadd.restcountries.utility.view.getChildAtOrNull
import pl.valueadd.restcountries.utility.view.setVisible
import pl.valueadd.restcountries.utility.view.toolbar.CollapsingToolbarState
import pl.valueadd.restcountries.utility.view.toolbar.onCollapsingToolbarStateChanged
import pl.valueadd.restcountries.view.chip.BorderChip
import javax.inject.Inject

class CountryDetailsFragment : BackMVPViewStateFragment<CountryDetailsView, CountryDetailsPresenter, CountryDetailsViewState>(R.layout.country_fragment_details),
    CountryDetailsView {

    companion object {

        private const val ARG_COUNTRY_ID = "ARG_COUNTRY_ID"
        private const val MAP_VIEW_BUNDLE = "MAP_VIEW_BUNDLE"

        fun createInstance(countryId: String): CountryDetailsFragment =
            CountryDetailsFragment().also {
                it.arguments = Bundle().apply {
                    putString(ARG_COUNTRY_ID, countryId)
                }
                it.enterTransition = Fade()
                it.exitTransition = Fade()
            }
    }

    /**
     * Keep reference to the view because
     * it is needed to invoke callbacks
     */
    private lateinit var mapView: MapView

    @Inject
    override lateinit var mPresenter: CountryDetailsPresenter

    override val toolbarNavigation: Toolbar
        get() = toolbar

    override val countryId: String
        by lazy { arguments?.getString(ARG_COUNTRY_ID) ?: EMPTY }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeMapView(savedInstanceState)
        initializeToolbar()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun createViewState(): CountryDetailsViewState =
        CountryDetailsViewState()

    override fun onSaveViewState() {
        viewState.apply {
            model = presenter.model
            borders = presenter.borderModels
            isBorderCardVisible = bordersCardView.isVisible
            isInformationCardVisible = informationCardView.isVisible
        }
    }

    override fun bindPositionDataToMapView(map: GoogleMap?, model: LatLngModel, name: String) {
        map?.run {
            val latLng = LatLng(model.lat, model.lng)

            val camera = CameraUpdateFactory.newLatLngZoom(latLng, BuildConfig.GOOGLE_MAP_DEFAULT_ZOOM)
            val marker = MarkerOptions()
                .position(latLng)

            addMarker(marker)
            moveCamera(camera)
        }
    }

    override fun bindModelToView(model: CountryModel) = model.run {
        setTitle(model.name)
        flagImageView.loadSVGImage(flagUrl)
        callingCodesPropertyView.subtitle = callingCodes.merge()
        domainsPropertyView.subtitle = topLevelDomains.merge()
        timeZonesPropertyView.subtitle = timezones.merge()
    }

    override fun bindBordersToView(models: List<CountryFlatModel>) {
        bordersChipGroup.removeAllViews()

        for (model in models) {
            val chip = createBorderChip(model)

            chip.throttleClicks()
                .onSuccess(disposables, { presenter.onBorderItemClick(model.id) })

            bordersChipGroup.addView(chip)
        }
    }

    override fun setBordersCardVisibility(isVisible: Boolean) =
        bordersCardView.setVisible(isVisible)

    override fun setInformationCardVisibility(isVisible: Boolean) =
        informationCardView.setVisible(isVisible)

    override fun setMapCardVisibility(isVisible: Boolean) =
        mapCardView.setVisible(isVisible)

    override fun navigateToCountry(countryId: String) {

        val fragment = createInstance(countryId)

        startWithPop(fragment)
    }

    private fun createBorderChip(model: CountryFlatModel): View =
        BorderChip(requireContext()).bindModel(model)

    private fun initializeToolbar() = toolbarNavigation.run {

        inflateMenu(R.menu.main_menu)
        setOnMenuItemClickListener(::onOptionsItemSelected)

        setNavigationIconBackground(CollapsingToolbarState.EXPANDED)

        appBarLayout.addOnOffsetChangedListener(onCollapsingToolbarStateChanged { _, state ->
            setNavigationIconBackground(state)
        })
    }

    private fun initializeMapView(savedState: Bundle?) {
        this.mapView = googleMapView

        val mapViewBundle: Bundle? = savedState?.getBundle(MAP_VIEW_BUNDLE)

        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(presenter)
    }

    private fun setNavigationIconBackground(@CollapsingToolbarState state: Int) {
        toolbarNavigation.getChildAtOrNull(INTEGER_ZERO)?.let { navigationIconView ->
            when (state) {
                CollapsingToolbarState.COLLAPSED -> {
                    navigationIconView.setBackgroundResource(R.color.whiteTransparent)
                }
                CollapsingToolbarState.EXPANDED -> {
                    navigationIconView.background = ContextCompat.getDrawable(requireContext(), R.drawable.country_background_navigation_icon)
                }
            }
        }
    }
}