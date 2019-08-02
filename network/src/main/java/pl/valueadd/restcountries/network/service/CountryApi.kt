package pl.valueadd.restcountries.network.service

import io.reactivex.Single
import pl.valueadd.restcountries.network.dto.country.CountryDto
import retrofit2.http.GET

interface CountryApi {

    @GET("all")
    fun getAllCountries() : Single<CountryDto>
}