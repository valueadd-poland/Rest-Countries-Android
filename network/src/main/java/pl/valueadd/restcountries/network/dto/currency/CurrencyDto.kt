package pl.valueadd.restcountries.network.dto.currency

import com.google.gson.annotations.SerializedName
import pl.valueadd.restcountries.network.dto.base.BaseDto

data class CurrencyDto(

    @SerializedName("code")
    var code: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("symbol")
    var symbol: String? = null

) : BaseDto()