package pl.valueadd.restcountries.network.dto.example

import pl.valueadd.restcountries.network.dto.base.BaseDto

data class ExampleDto(

    var id: Long? = null,

    var title: String? = null

) : BaseDto()