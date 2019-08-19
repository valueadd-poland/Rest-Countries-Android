package pl.valueadd.restcountries.network.dto.region

import com.google.gson.annotations.SerializedName
import pl.valueadd.restcountries.network.dto.base.BaseDto

data class RegionalBlocDto(

    @SerializedName("acronym")
    var acronym: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("otherAcronyms")
    var otherAcronyms: List<String>? = null,

    @SerializedName("otherNames")
    var otherNames: List<String>? = null

) : BaseDto()