package pl.valueadd.restcountries.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import pl.valueadd.restcountries.network.adapter.LatLngTypeAdapter
import pl.valueadd.restcountries.network.adapter.LatLngTypeAdapterProvider
import pl.valueadd.restcountries.network.definition.CallAdapterFactory
import pl.valueadd.restcountries.network.definition.ConverterFactory
import pl.valueadd.restcountries.network.definition.GsonInstance
import pl.valueadd.restcountries.network.definition.HttpLoggingLevel
import pl.valueadd.restcountries.network.definition.OkHttpBuilder
import pl.valueadd.restcountries.network.definition.OkHttpClientInstance
import pl.valueadd.restcountries.network.definition.RetrofitBuilder
import pl.valueadd.restcountries.network.definition.ServerUrl
import pl.valueadd.restcountries.network.http.ConverterFactoryProvider
import pl.valueadd.restcountries.network.http.GsonProvider
import pl.valueadd.restcountries.network.http.OkHttpClientBuilderProvider
import pl.valueadd.restcountries.network.http.OkHttpClientProvider
import pl.valueadd.restcountries.network.http.RetrofitBuilderProvider
import pl.valueadd.restcountries.network.http.RetrofitProvider
import pl.valueadd.restcountries.network.service.CountryApi
import pl.valueadd.restcountries.network.service.CountryApiProvider
import pl.valueadd.restcountries.utility.BuildConfig
import pl.valueadd.restcountries.utility.BuildConfig.SERVER_LOGGING_LEVEL
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import toothpick.config.Module

class NetworkModule : Module() {

    init {
        bindServiceProviders()
        bindAdapterProviders()

        bind(String::class.java)
            .withName(ServerUrl::class.java)
            .toInstance(provideServiceUrl())

        bind(HttpLoggingInterceptor.Level::class.java)
            .withName(HttpLoggingLevel::class.java)
            .toInstance(provideLogLevel())

        bind(Converter.Factory::class.java)
            .withName(ConverterFactory::class.java)
            .toProviderInstance(provideConverterFactoryProvider())

        bind(CallAdapter.Factory::class.java)
            .withName(CallAdapterFactory::class.java)

        bind(Gson::class.java)
            .withName(GsonInstance::class.java)
            .toProvider(GsonProvider::class.java)

        bind(OkHttpClient.Builder::class.java)
            .withName(OkHttpBuilder::class.java)
            .toProvider(OkHttpClientBuilderProvider::class.java)

        bind(OkHttpClient::class.java)
            .withName(OkHttpClientInstance::class.java)
            .toProvider(OkHttpClientProvider::class.java)

        bind(Retrofit.Builder::class.java)
            .withName(RetrofitBuilder::class.java)
            .toProvider(RetrofitBuilderProvider::class.java)

        bind(Retrofit::class.java)
            .toProvider(RetrofitProvider::class.java)
    }

    private fun bindServiceProviders() {
        bind(CountryApi::class.java).toProvider(CountryApiProvider::class.java)
    }

    private fun bindAdapterProviders() {
        bind(LatLngTypeAdapter::class.java).toProviderInstance(LatLngTypeAdapterProvider())
    }

    internal fun provideConverterFactoryProvider(): ConverterFactoryProvider =
        ConverterFactoryProvider()

    internal fun provideServiceUrl(): String =
        BuildConfig.SERVER_URL

    internal fun provideLogLevel(): Level =
        Level.valueOf(SERVER_LOGGING_LEVEL.trim().toUpperCase())
}
