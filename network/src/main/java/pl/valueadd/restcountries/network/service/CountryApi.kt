package pl.valueadd.restcountries.network.service

import pl.valueadd.restcountries.network.dto.country.CountryDto
import retrofit2.http.GET

interface CountryApi {

    @GET("all")
    suspend fun getAllCountries(): List<CountryDto>
}