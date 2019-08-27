package pl.valueadd.restcountries.utility.view.toolbar

import androidx.annotation.IntDef
import pl.valueadd.restcountries.utility.view.toolbar.CollapsingToolbarState.Companion.COLLAPSED
import pl.valueadd.restcountries.utility.view.toolbar.CollapsingToolbarState.Companion.EXPANDED
import pl.valueadd.restcountries.utility.view.toolbar.CollapsingToolbarState.Companion.IDLE

@IntDef(
    value = [
        IDLE,
        COLLAPSED,
        EXPANDED
    ]
)
@Retention(AnnotationRetention.SOURCE)
annotation class CollapsingToolbarState {

    companion object {

        const val IDLE = 0

        const val COLLAPSED = 1

        const val EXPANDED = 2
    }
}