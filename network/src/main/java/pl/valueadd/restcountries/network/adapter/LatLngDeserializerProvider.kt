package pl.valueadd.restcountries.network.adapter

import toothpick.ProvidesSingletonInScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@ProvidesSingletonInScope
class LatLngDeserializerProvider @Inject constructor() : Provider<LatLngDeserializer> {

    override fun get(): LatLngDeserializer =
        LatLngDeserializer()
}