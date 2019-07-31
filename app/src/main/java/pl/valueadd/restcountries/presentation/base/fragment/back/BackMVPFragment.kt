package pl.valueadd.restcountries.presentation.base.fragment.back

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.toolbar.toolbar
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.fragment.back.delegation.BackFragmentDelegate
import pl.valueadd.restcountries.presentation.base.fragment.back.delegation.BackFragmentDelegateCallback
import pl.valueadd.restcountries.presentation.base.fragment.back.delegation.BackFragmentDelegateImpl
import pl.valueadd.restcountries.presentation.base.fragment.base.BaseMVPFragment
import me.yokeyword.fragmentation.anim.FragmentAnimator
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.presentation.base.BaseView

abstract class BackMVPFragment<V : BaseView, P : BasePresenter<V>>(@LayoutRes layoutId: Int) :
    BaseMVPFragment<V, P>(layoutId),
    BackFragmentDelegateCallback,
    IBackFragment {

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