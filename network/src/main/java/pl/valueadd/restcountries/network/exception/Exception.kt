package pl.valueadd.restcountries.network.exception

import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.utility.exception.HttpCallException
import pl.valueadd.restcountries.utility.exception.UnavailableNetworkException
import pl.valueadd.restcountries.utility.exception.UnreachableServerException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Map common network errors.
 */
suspend fun <T> mapNetworkErrors(method: (suspend () -> T)): T {
    try {
        return method()
    } catch (error: Throwable) {
        when (error) {
            is SocketTimeoutException -> throw UnavailableNetworkException(error)
            is UnknownHostException -> throw UnreachableServerException(error)
            is HttpException -> throw HttpCallException(error,
                error.code(),
                error.response()?.errorBody()?.string() ?: EMPTY)
            else -> throw error
        }
    }
}
