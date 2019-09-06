package pl.valueadd.restcountries.utility.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlin.experimental.ExperimentalTypeInference

/*
 * Works like combineTransform from Zip.kt but collects more parameters.
 */
@Suppress("LongParameterList")
inline fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> combineTransform(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    @UseExperimental(ExperimentalTypeInference::class)
    @BuilderInference crossinline transform: suspend FlowCollector<R>.(T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit
): Flow<R> = kotlinx.coroutines.flow.combineTransform(flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9) { args: Array<*> ->
    @Suppress("MagicNumber")
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8,
        args[8] as T9
    )
}

inline fun CoroutineScope.throttleFirst(
    skipMs: Long = 400,
    crossinline destinationFunction: () -> Unit
): () -> Unit {
    var throttleJob: Job? = null
    return {
        if (throttleJob?.isCompleted != false) {
            throttleJob = launch {
                destinationFunction()
                delay(skipMs)
            }
        }
    }
}

fun CoroutineScope.cancelSafe(cause: CancellationException? = null) {
    val job = coroutineContext[Job] ?: return
    job.cancel(cause)
}