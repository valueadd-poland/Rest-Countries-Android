package pl.valueadd.restcountries.application

import androidx.multidex.MultiDexApplication
import pl.valueadd.restcountries.domain.DomainModule
import pl.valueadd.restcountries.utility.BuildConfig
import pl.valueadd.restcountries.utility.UtilityModule
import pl.valueadd.restcountries.utility.dependencyinjection.DependencyUtil
import pl.valueadd.restcountries.utility.image.GlideApp
import me.yokeyword.fragmentation.Fragmentation
import toothpick.config.Module

class RestCountriesApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        initToothpick()

        if (BuildConfig.DEBUG_BACKSTACK) {
            Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .handleException {
                    // TODO Crashlytics log exception...
                }
                .install()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        GlideApp.get(this).onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        GlideApp.get(this).onTrimMemory(level)
    }

    private fun initToothpick() =
        DependencyUtil.createAppScopeAndInject(this, *provideModules())

    private fun provideModules(): Array<Module> =
        arrayOf(
            pl.valueadd.restcountries.application.ApplicationModule(this),
            pl.valueadd.restcountries.presentation.PresentationModule(),
            DomainModule(),
            *DomainModule.provideDependencies(this),
            UtilityModule()
        )
}