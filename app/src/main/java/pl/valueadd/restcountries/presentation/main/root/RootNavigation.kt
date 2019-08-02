package pl.valueadd.restcountries.presentation.main.root

import androidx.annotation.IntDef
import pl.valueadd.restcountries.presentation.main.root.RootNavigation.Type.COUNTRIES

@IntDef(
    value = [
        COUNTRIES
    ]
)
@Retention(AnnotationRetention.SOURCE)
annotation class RootNavigation {

    companion object Type {

        const val COUNTRIES = 0

    }
}