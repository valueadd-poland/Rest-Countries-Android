package pl.valueadd.restcountries.utility.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

/**
 * Returns a flow containing the results of applying the given [transform] function to each value of the original flow.
 */
inline fun <T, R> Flow<T>.map(crossinline transform: (value: T) -> R): Flow<R> = transform { value ->
    return@transform emit(transform(value))
}