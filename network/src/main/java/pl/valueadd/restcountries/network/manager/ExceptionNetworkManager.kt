package pl.valueadd.restcountries.network.manager

import android.content.Context
import pl.valueadd.restcountries.network.definition.GsonInstance
import pl.valueadd.restcountries.utility.exception.HttpCallException
import pl.valueadd.restcountries.utility.exception.NetworkException
import pl.valueadd.restcountries.utility.exception.NetworkException.Constants.BAD_REQUEST
import pl.valueadd.restcountries.utility.exception.NetworkException.Constants.FORBIDDEN
import pl.valueadd.restcountries.utility.exception.NetworkException.Constants.INTERNAL_SERVER_ERROR
import pl.valueadd.restcountries.utility.exception.NetworkException.Constants.NOT_FOUND
import pl.valueadd.restcountries.utility.exception.NetworkException.Constants.UNAUTHORIZED
import pl.valueadd.restcountries.utility.exception.NetworkException.Constants.UNDEFINED
import pl.valueadd.restcountries.utility.exception.UnavailableNetworkException
import pl.valueadd.restcountries.utility.exception.UnreachableServerException
import com.google.gson.Gson
import pl.valueadd.restcountries.network.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExceptionNetworkManager @Inject constructor(
    @GsonInstance private val gson: Gson,
    private val context: Context
) {

    fun mapToMessage(throwable: Throwable): String {
        return when (throwable) {
            is UnavailableNetworkException -> context.getString(R.string.exception_network_not_available)
            is UnreachableServerException -> context.getString(R.string.exception_server_connection_failed)
            is HttpCallException -> mapToMessage(throwable)
            else -> if (throwable.localizedMessage.isNullOrBlank()) {
                context.getString(R.string.exception_unknown)
            } else throwable.localizedMessage
        }
    }

    @Suppress("MagicNumber")
    fun mapToMessage(error: HttpCallException): String {
        return when (error.statusCode) {
            BAD_REQUEST -> context.getString(R.string.exception_400)
            FORBIDDEN -> context.getString(R.string.exception_403)
            INTERNAL_SERVER_ERROR -> context.getString(R.string.exception_500)
            NOT_FOUND -> context.getString(R.string.exception_404)
            UNAUTHORIZED -> context.getString(R.string.exception_401)
            UNDEFINED -> context.getString(R.string.exception_unknown)
            else -> when {
                error.statusCode in 400 until 500 -> context.getString(R.string.exception_400plus)
                error.statusCode in 500 until 600 -> context.getString(R.string.exception_500plus)
                else -> context.getString(R.string.exception_unknown)
            }
        }
    }

    fun <T> mapErrorBody(error: NetworkException, clazz: Class<T>): T? {
        return if (error is HttpCallException && error.statusCode >= BAD_REQUEST) {
            try {
                gson.fromJson(error.errorBody, clazz)
            } catch (exception: Exception) {
                null
            }
        } else null
    }
}