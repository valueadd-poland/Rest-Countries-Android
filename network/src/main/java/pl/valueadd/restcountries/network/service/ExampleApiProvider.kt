package pl.valueadd.restcountries.network.service

import retrofit2.Retrofit
import toothpick.ProvidesSingletonInScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@ProvidesSingletonInScope
class ExampleApiProvider @Inject constructor(
    private val retrofit: Retrofit
) : Provider<ExampleApi> {
    override fun get(): ExampleApi =
        retrofit.create(ExampleApi::class.java)
}