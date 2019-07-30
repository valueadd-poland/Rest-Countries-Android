package pl.valueadd.restcountries.presentation.main

import android.os.Bundle
import pl.valueadd.restcountries.presentation.base.activity.BaseMVPActivity
import pl.valueadd.restcountries.presentation.main.root.RootFragment
import javax.inject.Inject

class MainActivity : BaseMVPActivity<MainView, MainPresenter>(),
    MainView {

    @Inject
    override lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadRootFragment()
    }

    private fun loadRootFragment() {

        val fragment = findFragment(RootFragment::class.java)

        if (fragment == null) {
            loadRootFragment(fragmentContainer, RootFragment.createInstance())
        }
    }
}