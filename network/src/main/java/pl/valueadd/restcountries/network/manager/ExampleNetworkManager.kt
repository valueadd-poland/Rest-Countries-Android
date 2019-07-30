package pl.valueadd.restcountries.network.manager

import pl.valueadd.restcountries.network.dto.example.ExampleDto
import pl.valueadd.restcountries.network.exception.mapNetworkErrors
import pl.valueadd.restcountries.network.service.ExampleApi
import io.reactivex.Single
import io.reactivex.annotations.SchedulerSupport
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class ExampleNetworkManager @Inject constructor(
    private val api: ExampleApi
) {

    fun getExamples(): Single<List<ExampleDto>> {
        return api
            .getExamples()
            .mapNetworkErrors()
    }

    @Suppress("MagicNumber")
    fun getMockedExamples(): Single<List<ExampleDto>> =
        Single.fromCallable {
            listOf(
                ExampleDto(
                    Math.random().times(1_000).toLong(),
                    "Title no. ${Math.random().times(1_000)}"
                ),
                ExampleDto(
                    Math.random().times(1_000).toLong(),
                    "Title no. ${Math.random().times(1_000)}"
                ),
                ExampleDto(
                    Math.random().times(1_000).toLong(),
                    "Title no. ${Math.random().times(1_000)}"
                )
            )
        }.delay(2, TimeUnit.SECONDS)
            .mapNetworkErrors()
}