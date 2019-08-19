package pl.valueadd.restcountries.network.adapter

import toothpick.ProvidesSingletonInScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@ProvidesSingletonInScope
class LatLngTypeAdapterProvider @Inject constructor() : Provider<LatLngTypeAdapter> {

    override fun get(): LatLngTypeAdapter =
        LatLngTypeAdapter()
}