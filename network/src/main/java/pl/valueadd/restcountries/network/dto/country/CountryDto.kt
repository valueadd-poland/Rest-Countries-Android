package pl.valueadd.restcountries.network.dto.country

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import pl.valueadd.restcountries.network.adapter.LatLngTypeAdapter
import pl.valueadd.restcountries.network.dto.base.BaseDto
import pl.valueadd.restcountries.network.dto.currency.CurrencyDto
import pl.valueadd.restcountries.network.dto.language.LanguageDto
import pl.valueadd.restcountries.network.dto.language.TranslationsDto
import pl.valueadd.restcountries.network.dto.region.RegionalBlocDto

data class CountryDto(

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("topLevelDomain")
    var topLevelDomains: List<String>? = null,

    @SerializedName("alpha2Code")
    var alpha2Code: String? = null,

    @SerializedName("alpha3Code")
    var alpha3Code: String? = null,

    @SerializedName("callingCodes")
    var callingCodes: List<String>? = null,

    @SerializedName("capital")
    var capital: String? = null,

    @SerializedName("altSpellings")
    var altSpellings: List<String>? = null,

    @SerializedName("region")
    var region: String? = null,

    @SerializedName("subregion")
    var subRegion: String? = null,

    @SerializedName("population")
    var population: Long? = null,

    @SerializedName("latlng")
    @JsonAdapter(value = LatLngTypeAdapter::class, nullSafe = false)
    var latLng: LatLngDto? = null,

    @SerializedName("demonym")
    var demonym: String? = null,

    @SerializedName("area")
    var area: Double? = null,

    @SerializedName("gini")
    var gini: Double? = null,

    @SerializedName("timezones")
    var timezones: List<String>? = null,

    @SerializedName("borders")
    var borders: List<String>? = null,

    @SerializedName("nativeName")
    var nativeName: String? = null,

    @SerializedName("numericCode")
    var numericCode: String? = null,

    @SerializedName("currencies")
    var currencies: List<CurrencyDto>? = null,

    @SerializedName("languages")
    var languages: List<LanguageDto>? = null,

    @SerializedName("translations")
    var translations: TranslationsDto? = null,

    @SerializedName("flag")
    var flagUrl: String? = null,

    @SerializedName("regionalBlocs")
    var regionalBlocs: List<RegionalBlocDto>? = null,

    @SerializedName("cioc")
    var cioc: String? = null

) : BaseDto()