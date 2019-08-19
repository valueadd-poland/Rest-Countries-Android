package pl.valueadd.restcountries.network.service

import retrofit2.Retrofit
import toothpick.ProvidesSingletonInScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@ProvidesSingletonInScope
class CountryApiProvider @Inject constructor(
    private val retrofit: Retrofit
) : Provider<CountryApi> {

    override fun get(): CountryApi =
        retrofit.create(CountryApi::class.java)
}