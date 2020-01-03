package pl.valueadd.restcountries.presentation.base.fragment.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.utility.view.snackbar.SnackbarUtil
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegate
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.presentation.base.BaseView
import javax.inject.Inject

abstract class BaseMVPFragment<V : BaseView, P : BasePresenter<V>>(@LayoutRes layoutId: Int) :
    BaseFragment(layoutId),
    MvpDelegateCallback<V, P>,
    BaseView {

    @Inject
    lateinit var snackBarUtil: SnackbarUtil

    protected fun showError(error: String, view: View) {
        snackBarUtil.showMessage(view, error, ContextCompat.getColor(requireContext(), R.color.red))
    }

    /* MvpFragment */

    protected open val mvpDelegate: FragmentMvpDelegate<V, P>
        by lazy { FragmentMvpDelegateImpl<V, P>(this, this, true, true) }

    abstract var mPresenter: P

    override fun createPresenter(): P =
        mPresenter

    override fun getPresenter(): P =
        mPresenter

    override fun setPresenter(presenter: P) {
        this.mPresenter = presenter
    }

    @Suppress("UNCHECKED_CAST")
    override fun getMvpView(): V =
        this as V

    /* BaseView */

    override fun showError(message: String) =
        showError(message, requireActivity().findViewById(android.R.id.content))

    /* Life cycle */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpDelegate.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mvpDelegate.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        presenter.clearDisposables()
        clearDisposables()

        super.onDestroy()
        mvpDelegate.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mvpDelegate.onPause()
    }

    override fun onResume() {
        super.onResume()
        mvpDelegate.onResume()
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate.onStart()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onStop()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpDelegate.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mvpDelegate.onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        mvpDelegate.onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
    }
}