package pl.valueadd.restcountries.utility.view.toolbar

import com.google.android.material.appbar.AppBarLayout
import org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO
import pl.valueadd.restcountries.utility.view.toolbar.CollapsingToolbarState.Companion.COLLAPSED
import pl.valueadd.restcountries.utility.view.toolbar.CollapsingToolbarState.Companion.EXPANDED
import pl.valueadd.restcountries.utility.view.toolbar.CollapsingToolbarState.Companion.IDLE
import kotlin.math.abs

inline fun onCollapsingToolbarStateChanged(
    crossinline action: OnCollapsingToolbarStateChanged.(appBar: AppBarLayout, state: Int) -> Unit
): OnCollapsingToolbarStateChanged = object : OnCollapsingToolbarStateChanged() {
    override fun onStateChanged(appBar: AppBarLayout, state: Int) {
        action(appBar, state)
    }
}

abstract class OnCollapsingToolbarStateChanged : AppBarLayout.OnOffsetChangedListener {

    @CollapsingToolbarState
    private val state: Int = IDLE

    override fun onOffsetChanged(appBar: AppBarLayout, offset: Int) {
        when {
            offset == INTEGER_ZERO ->
                invokeListener(EXPANDED, appBar)
            abs(offset) >= appBar.totalScrollRange ->
                invokeListener(COLLAPSED, appBar)
            else ->
                invokeListener(IDLE, appBar)
        }
    }

    private fun invokeListener(newSate: Int, appBar: AppBarLayout) {
        if (state != newSate) {
            onStateChanged(appBar, newSate)
        }
    }

    abstract fun onStateChanged(appBar: AppBarLayout, @CollapsingToolbarState state: Int)
}