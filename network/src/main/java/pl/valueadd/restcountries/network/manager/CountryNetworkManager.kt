package pl.valueadd.restcountries.network.manager

import pl.valueadd.restcountries.network.dto.country.CountryDto
import pl.valueadd.restcountries.network.service.CountryApi
import pl.valueadd.restcountries.network.exception.mapNetworkErrors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryNetworkManager @Inject constructor(private val api: CountryApi) {

    suspend fun getAllCountries(): List<CountryDto> =
        mapNetworkErrors(api::getAllCountries)
}