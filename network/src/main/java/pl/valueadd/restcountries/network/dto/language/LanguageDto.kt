package pl.valueadd.restcountries.network.dto.language

import com.google.gson.annotations.SerializedName
import pl.valueadd.restcountries.network.dto.base.BaseDto

data class LanguageDto(

    @SerializedName("iso6391")
    var iso6391: String? = null,

    @SerializedName("iso6392")
    var iso6392: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("nativeName")
    var nativeName: String? = null

) : BaseDto()