package pl.valueadd.restcountries.network.service

import pl.valueadd.restcountries.network.dto.example.ExampleDto
import io.reactivex.Single
import retrofit2.http.GET

interface ExampleApi {

    @GET("/api/examples")
    fun getExamples(): Single<List<ExampleDto>>
}