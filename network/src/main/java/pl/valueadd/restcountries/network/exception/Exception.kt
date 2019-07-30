package pl.valueadd.restcountries.network.exception

import pl.valueadd.restcountries.utility.exception.HttpCallException
import pl.valueadd.restcountries.utility.exception.UnavailableNetworkException
import pl.valueadd.restcountries.utility.exception.UnreachableServerException
import io.reactivex.Completable
import io.reactivex.Single
import org.apache.commons.lang3.StringUtils.EMPTY
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Map common network errors.
 * @return [Completable]
 */
fun Completable.mapNetworkErrors(): Completable =
    this.onErrorResumeNext { error ->
        when (error) {
            is SocketTimeoutException -> Completable.error(UnavailableNetworkException(error))
            is UnknownHostException -> Completable.error(UnreachableServerException(error))
            is HttpException -> Completable.error(
                HttpCallException(error,
                    error.code(),
                    error.response()?.errorBody()?.string() ?: EMPTY)
            )
            else -> Completable.error(error)
        }
    }

/**
 * Map common network errors.
 * @return [Single]<*>
 */
fun <T> Single<T>.mapNetworkErrors(): Single<T> =
    this.onErrorResumeNext { error ->
        when (error) {
            is SocketTimeoutException -> Single.error(UnavailableNetworkException(error))
            is UnknownHostException -> Single.error(UnreachableServerException(error))
            is HttpException -> Single.error(
                HttpCallException(error,
                    error.code(),
                    error.response()?.errorBody()?.string() ?: EMPTY)
            )
            else -> Single.error(error)
        }
    }