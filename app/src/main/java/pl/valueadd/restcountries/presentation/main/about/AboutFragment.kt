package pl.valueadd.restcountries.presentation.main.about

import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.fragment.back.BackMVPFragment
import javax.inject.Inject

class AboutFragment : BackMVPFragment<AboutView, AboutPresenter>(R.layout.fragment_about),
    AboutView {

    companion object {

        fun createInstance(): AboutFragment =
            AboutFragment()
    }

    @Inject
    override lateinit var mPresenter: AboutPresenter

    override val titleRes: Int =
        R.string.about_title
}
