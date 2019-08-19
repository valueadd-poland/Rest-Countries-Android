package pl.valueadd.restcountries.presentation.main.root

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import kotlinx.android.synthetic.main.toolbar.toolbar
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.fragment.base.BaseMVPFragment
import pl.valueadd.restcountries.presentation.base.fragment.base.IBaseFragment
import pl.valueadd.restcountries.presentation.main.countries.list.CountryListFragment
import javax.inject.Inject

class RootFragment : BaseMVPFragment<RootView, RootPresenter>(R.layout.root_fragment),
    RootView {

    companion object {

        fun createInstance(): RootFragment =
            RootFragment()

        private const val ROOT_NAVIGATION_SIZE = 1
    }

    @Inject
    override lateinit var mPresenter: RootPresenter

    @IdRes
    private val childFragmentContainer: Int = R.id.fragmentContainer

    private val rootFragments: Array<IBaseFragment?> = arrayOfNulls(ROOT_NAVIGATION_SIZE)

    @RootNavigation
    private var currentRootNavigation: Int = RootNavigation.COUNTRIES

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar()

        loadRootFragments()
    }

    private fun loadRootFragments() {

        val first = findChildFragment(getRootFragmentClass(RootNavigation.COUNTRIES))

        if (first == null) {
            rootFragments[RootNavigation.COUNTRIES] = CountryListFragment.createInstance()

            supportDelegate.loadMultipleRootFragment(
                childFragmentContainer, RootNavigation.COUNTRIES,
                rootFragments[RootNavigation.COUNTRIES]
            )
        } else {
            rootFragments[RootNavigation.COUNTRIES] = first
        }
    }

    private fun getRootFragmentClass(@RootNavigation type: Int): Class<out IBaseFragment> =
        when (type) {
            RootNavigation.COUNTRIES -> CountryListFragment::class.java
            else -> throw IllegalArgumentException("Invalid type ($type) has been passed.")
        }

    private fun setToolbarTitle(@StringRes resId: Int): Unit =
        toolbar.setTitle(resId)

    private fun navigateToView(@RootNavigation type: Int) {

        val currentFragment: IBaseFragment = requireNotNull(rootFragments[currentRootNavigation])

        val selectedFragment: IBaseFragment = requireNotNull(rootFragments[type])

        showHideFragment(
            showFragment = selectedFragment,
            hideFragment = currentFragment
        )

        currentRootNavigation = type
    }

    private fun initializeToolbar() = toolbar.run {

        inflateMenu(R.menu.main_menu)

        setOnMenuItemClickListener(::onOptionsItemSelected)

        setToolbarTitle(R.string.country_list_title)
    }
}