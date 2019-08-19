package pl.valueadd.restcountries.network.dto.language

import com.google.gson.annotations.SerializedName
import pl.valueadd.restcountries.network.dto.base.BaseDto

data class TranslationsDto(

    @SerializedName("de")
    var de: String? = null,

    @SerializedName("es")
    var es: String? = null,

    @SerializedName("fr")
    var fr: String? = null,

    @SerializedName("ja")
    var ja: String? = null,

    @SerializedName("it")
    var it: String? = null,

    @SerializedName("br")
    var br: String? = null,

    @SerializedName("pt")
    var pt: String? = null,

    @SerializedName("nl")
    var nl: String? = null,

    @SerializedName("hr")
    var hr: String? = null,

    @SerializedName("fa")
    var fa: String? = null

) : BaseDto()