package pl.valueadd.restcountries.network.manager

import io.reactivex.Single
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.network.dto.country.CountryDto
import pl.valueadd.restcountries.network.exception.mapNetworkErrors
import pl.valueadd.restcountries.network.service.CountryApi
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class CountryNetworkManager @Inject constructor(private val api: CountryApi) {

    fun getAllCountries(): Single<List<CountryDto>> =
        api.getAllCountries()
            .mapNetworkErrors()
            .subscribeOnIo()
}