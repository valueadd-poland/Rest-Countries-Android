package pl.valueadd.restcountries.presentation.base.fragment.back

import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import pl.valueadd.restcountries.presentation.base.fragment.base.IBaseFragment

interface IBackFragment : IBaseFragment {

    fun setTitle(@StringRes resId: Int)

    fun setTitle(title: String)

    fun initializeToolbarNavigation(toolbar: Toolbar)

    fun initializeToolbarMenu(toolbar: Toolbar)
}