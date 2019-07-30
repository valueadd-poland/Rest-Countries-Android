package pl.valueadd.restcountries.presentation.base.fragment.viewstate.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import pl.valueadd.restcountries.presentation.base.fragment.base.BaseMVPFragment
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.BaseViewState
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegate
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpViewStateDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.MvpViewStateDelegateCallback

abstract class BaseMVPViewStateFragment<V : pl.valueadd.restcountries.presentation.base.BaseView, P : pl.valueadd.restcountries.presentation.base.BasePresenter<V>, VS : BaseViewState<V>>(@LayoutRes layoutId: Int) :
    BaseMVPFragment<V, P>(layoutId),
    MvpViewStateDelegateCallback<V, P, VS>,
    IBaseViewStateFragment {

    private lateinit var stateOfView: VS

    private var restoringStateOfView = false

    override val mvpDelegate: FragmentMvpDelegate<V, P>
        by lazy { FragmentMvpViewStateDelegateImpl(this, this, true, true) }

    override fun setViewState(viewState: VS) {
        this.stateOfView = viewState
    }

    override fun setRestoringViewState(restoringViewState: Boolean) {
        this.restoringStateOfView = restoringViewState
    }

    override fun getViewState(): VS =
        this.stateOfView

    override fun onViewStateInstanceRestored(instanceStateRetained: Boolean) {
        // not needed. You could override this is subclasses if needed
    }

    override fun onNewViewStateInstance() {
        // not needed. You could override this is subclasses if needed
    }

    override fun isRestoringViewState(): Boolean =
        this.restoringStateOfView

    override fun onSaveInstanceState(outState: Bundle) {
        this.onSaveViewState()
        super.onSaveInstanceState(outState)
    }

    /**
     * Invoked before super.[onSaveInstanceState].
     * Should be used to save the data into [ViewState][VS].
     */
    protected open fun onSaveViewState() {
        // not needed.
    }
}