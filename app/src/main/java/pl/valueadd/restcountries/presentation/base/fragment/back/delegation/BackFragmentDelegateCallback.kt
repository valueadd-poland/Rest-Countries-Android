package pl.valueadd.restcountries.presentation.base.fragment.back.delegation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar

interface BackFragmentDelegateCallback {

    @get:StringRes
    val titleRes: Int

    @get:DrawableRes
    val navigationIcon: Int

    val toolbarNavigation: Toolbar

    fun setTitle(@StringRes resId: Int)

    fun setTitle(title: String)

    fun initializeToolbarNavigation(toolbar: Toolbar)

    fun initializeToolbarMenu(toolbar: Toolbar)
}