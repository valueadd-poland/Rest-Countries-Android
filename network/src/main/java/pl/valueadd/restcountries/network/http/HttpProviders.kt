package pl.valueadd.restcountries.network.http

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.valueadd.restcountries.network.adapter.LatLngTypeAdapter
import pl.valueadd.restcountries.network.definition.CallAdapterFactory
import pl.valueadd.restcountries.network.definition.ConverterFactory
import pl.valueadd.restcountries.network.definition.HttpLoggingLevel
import pl.valueadd.restcountries.network.definition.OkHttpBuilder
import pl.valueadd.restcountries.network.definition.RetrofitBuilder
import pl.valueadd.restcountries.network.definition.ServerUrl
import pl.valueadd.restcountries.network.dto.country.LatLngDto
import pl.valueadd.restcountries.network.interceptor.HeaderAuthorizationInterceptor
import pl.valueadd.restcountries.utility.BuildConfig
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.ProvidesSingletonInScope
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@ProvidesSingletonInScope
class RetrofitProvider @Inject constructor(
    @ServerUrl private val url: String,
    @RetrofitBuilder private val builder: Retrofit.Builder
) : Provider<Retrofit> {

    override fun get(): Retrofit =
        builder
            .baseUrl(url)
            .build()
}

@Singleton
@ProvidesSingletonInScope
class RetrofitBuilderProvider @Inject constructor(
    @CallAdapterFactory private val callAdapterFactory: CallAdapter.Factory,
    @ConverterFactory private val converterFactory: Converter.Factory,
    @pl.valueadd.restcountries.network.definition.OkHttpClientInstance private val client: OkHttpClient
) : Provider<Retrofit.Builder> {

    override fun get(): Retrofit.Builder =
        Retrofit.Builder()
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(converterFactory)
            .client(client)
}

@Singleton
@ProvidesSingletonInScope
class GsonProvider @Inject constructor(
    private val latLngTypeAdapter: LatLngTypeAdapter
) : Provider<Gson> {

    override fun get(): Gson =
        GsonBuilder()
            .setLenient()
            .setDateFormat(BuildConfig.SERVER_TIME_FORMAT)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(LatLngDto::class.java, latLngTypeAdapter)
            .create()
}

@Singleton
@ProvidesSingletonInScope
class OkHttpClientProvider @Inject constructor(
    @OkHttpBuilder private val builder: OkHttpClient.Builder
) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient =
        builder.build()
}

@Singleton
@ProvidesSingletonInScope
class CallAdapterFactoryProvider : Provider<CallAdapter.Factory> {

    override fun get(): CallAdapter.Factory =
        RxJava2CallAdapterFactory.create()
}

@Singleton
@ProvidesSingletonInScope
class ConverterFactoryProvider : Provider<Converter.Factory> {

    override fun get(): Converter.Factory =
        GsonConverterFactory.create()
}

@Singleton
@ProvidesSingletonInScope
class OkHttpClientBuilderProvider @Inject constructor(
    private val headerAuthorizationInterceptor: HeaderAuthorizationInterceptor,
    @HttpLoggingLevel private val loggingLevel: HttpLoggingInterceptor.Level
) : Provider<OkHttpClient.Builder> {

    override fun get(): OkHttpClient.Builder {

        val loginInterceptor = HttpLoggingInterceptor().apply { level = loggingLevel }

        return OkHttpClient.Builder()
            .connectTimeout(BuildConfig.SERVER_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(BuildConfig.SERVER_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(BuildConfig.SERVER_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loginInterceptor)
            .addInterceptor(headerAuthorizationInterceptor)
    }
}