package pl.valueadd.restcountries.presentation.base.fragment.back.delegation

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import pl.valueadd.restcountries.R

class BackFragmentDelegateImpl(
    private val fragment: Fragment,
    private val delegateCallback: BackFragmentDelegateCallback
) : BackFragmentDelegate {

    override val toolbarNavigation: Toolbar
        get() = delegateCallback.toolbarNavigation

    @get:StringRes
    override val titleRes: Int
        get() = delegateCallback.titleRes

    @get:DrawableRes
    override val navigationIcon: Int
        get() = delegateCallback.navigationIcon

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        delegateCallback.initializeToolbarNavigation(toolbarNavigation)
        delegateCallback.setTitle(titleRes)
    }

    override fun setTitle(@StringRes resId: Int): Unit =
        delegateCallback.setTitle(getString(resId))

    override fun setTitle(title: String) {
        delegateCallback.toolbarNavigation.title = title
    }

    override fun initializeToolbarNavigation(toolbar: Toolbar) {
        toolbar.apply {
            setNavigationIcon(this@BackFragmentDelegateImpl.navigationIcon)
            setNavigationOnClickListener {
                fragment.requireActivity().onBackPressed()
            }
            initializeToolbarMenu(this)
        }
    }

    override fun initializeToolbarMenu(toolbar: Toolbar): Unit = toolbar.run {
        inflateMenu(R.menu.main_menu)
        setOnMenuItemClickListener(fragment::onOptionsItemSelected)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator =
        DefaultVerticalAnimator()

    private fun getString(@StringRes resId: Int): String =
        fragment.getString(resId)
}