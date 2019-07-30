package pl.valueadd.restcountries.utility.exception

import org.apache.commons.lang3.StringUtils.EMPTY

open class NetworkException(
    throwable: Throwable,
    val statusCode: Int = UNDEFINED,
    val errorBody: String = EMPTY
) : RuntimeException(errorBody, throwable) {

    companion object Constants {
        const val UNDEFINED: Int = -1
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER_ERROR = 500
    }
}

class UnavailableNetworkException(throwable: Throwable) : NetworkException(throwable)

class UnreachableServerException(throwable: Throwable) : NetworkException(throwable)

class HttpCallException(throwable: Throwable, statusCode: Int, errorBody: String) :
    NetworkException(throwable, statusCode, errorBody)