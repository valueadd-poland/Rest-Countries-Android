package pl.valueadd.restcountries.network.interceptor

import pl.valueadd.restcountries.utility.network.AccessToken
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderAuthorizationInterceptor @Inject constructor(private val token: AccessToken) : Interceptor {

    companion object {

        const val AUTH_HEADER = "Authorization"

        const val AUTH_PREFIX = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val accessToken = token.code

        return if (accessToken.isNotBlank()) {
            val updated = original
                .newBuilder()
                .addHeader(AUTH_HEADER, "$AUTH_PREFIX $accessToken")
                .build()
            chain.proceed(updated)
        } else {
            chain.proceed(original)
        }
    }
}