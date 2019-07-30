package pl.valueadd.restcountries.presentation.main.first

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_first.aboutButton
import kotlinx.android.synthetic.main.fragment_first.addButton
import kotlinx.android.synthetic.main.fragment_first.counterText
import kotlinx.android.synthetic.main.fragment_first.removeButton
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.fragment.base.BaseMVPFragment
import pl.valueadd.restcountries.presentation.main.about.AboutFragment
import pl.valueadd.restcountries.presentation.main.root.RootFragment
import pl.valueadd.restcountries.utility.reactivex.onSuccess
import pl.valueadd.restcountries.utility.reactivex.throttleClicks
import javax.inject.Inject

class FirstFragment : BaseMVPFragment<FirstView, FirstPresenter>(R.layout.fragment_first),
    FirstView {

    companion object {
        fun createInstance(): FirstFragment =
            FirstFragment()
    }

    @Inject
    override lateinit var mPresenter: FirstPresenter

    private val buttonDelay
        by lazy { resources.getInteger(R.integer.button_delay_100).toLong() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindListeners()
    }

    override fun bindCountToView(count: Int) {
        counterText.text = count.toString()
    }
    override fun navigateToAboutView() {
        val fragment = AboutFragment.createInstance()

        getParentFragment(RootFragment::class.java)
            .start(fragment)
    }

    private fun bindListeners() {
        aboutButton
            .throttleClicks()
            .onSuccess(disposables, { presenter.onAboutButtonClick() })
        addButton
            .throttleClicks(buttonDelay)
            .onSuccess(disposables, { presenter.increaseSelectedCountOfExamples() })
        removeButton
            .throttleClicks(buttonDelay)
            .onSuccess(disposables, { presenter.decreaseSelectedCountOfExamples() })
    }
}