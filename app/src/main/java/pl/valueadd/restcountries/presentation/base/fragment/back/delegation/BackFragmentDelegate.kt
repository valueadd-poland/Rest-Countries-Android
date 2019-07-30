package pl.valueadd.restcountries.presentation.base.fragment.back.delegation

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import me.yokeyword.fragmentation.anim.FragmentAnimator

interface BackFragmentDelegate {

    fun onViewCreated(view: View, savedInstanceState: Bundle?)

    fun setTitle(@StringRes resId: Int)

    fun setTitle(title: String)

    fun initializeToolbarNavigation(toolbar: Toolbar)

    fun initializeToolbarMenu(toolbar: Toolbar)

    fun onCreateFragmentAnimator(): FragmentAnimator
}