package pl.valueadd.restcountries.presentation.base.fragment.viewstate.back

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.toolbar.toolbar
import me.yokeyword.fragmentation.anim.FragmentAnimator
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.presentation.base.BaseView
import pl.valueadd.restcountries.presentation.base.fragment.back.delegation.BackFragmentDelegate
import pl.valueadd.restcountries.presentation.base.fragment.back.delegation.BackFragmentDelegateImpl
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.BaseViewState
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.base.BaseMVPViewStateFragment

abstract class BackMVPViewStateFragment<V : BaseView, P : BasePresenter<V>, VS : BaseViewState<V>>(@LayoutRes layoutId: Int) :
    BaseMVPViewStateFragment<V, P, VS>(layoutId),
    IBackViewStateFragment {

    private val backDelegate: BackFragmentDelegate
        by lazy { BackFragmentDelegateImpl(this, this) }

    override val titleRes: Int =
        R.string.common_empty

    override val navigationIcon: Int =
        R.drawable.ic_arrow_back_white_24dp

    override val toolbarNavigation: Toolbar
        get() = toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backDelegate.onViewCreated(view, savedInstanceState)
    }

    override fun setTitle(@StringRes resId: Int) =
        backDelegate.setTitle(resId)

    override fun setTitle(title: String) =
        backDelegate.setTitle(title)

    override fun initializeToolbarNavigation(toolbar: Toolbar) =
        backDelegate.initializeToolbarNavigation(toolbar)

    override fun initializeToolbarMenu(toolbar: Toolbar) =
        backDelegate.initializeToolbarMenu(toolbar)

    override fun onCreateFragmentAnimator(): FragmentAnimator =
        backDelegate.onCreateFragmentAnimator()
}