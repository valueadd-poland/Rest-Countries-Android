package pl.valueadd.restcountries.presentation.main.third

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_third.aboutButton
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.fragment.base.BaseMVPFragment
import pl.valueadd.restcountries.presentation.main.about.AboutFragment
import pl.valueadd.restcountries.presentation.main.root.RootFragment
import pl.valueadd.restcountries.utility.reactivex.onSuccess
import pl.valueadd.restcountries.utility.reactivex.throttleClicks
import javax.inject.Inject

class ThirdFragment : BaseMVPFragment<ThirdView, ThirdPresenter>(R.layout.fragment_third),
    ThirdView {

    companion object {

        fun createInstance(): ThirdFragment =
            ThirdFragment()
    }

    @Inject
    override lateinit var mPresenter: ThirdPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindListeners()
    }

    override fun navigateToAboutView() {
        val fragment = AboutFragment.createInstance()

        getParentFragment(RootFragment::class.java)
            .start(fragment)
    }

    private fun bindListeners() {
        aboutButton
            .throttleClicks()
            .onSuccess(disposables, { mPresenter.onAboutButtonClick() })
    }
}