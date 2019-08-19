package pl.valueadd.restcountries.domain.model.country

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO
import pl.valueadd.restcountries.domain.model.base.BaseModel
import pl.valueadd.restcountries.domain.model.currency.CurrencyModel
import pl.valueadd.restcountries.domain.model.language.LanguageModel
import pl.valueadd.restcountries.domain.model.language.TranslationsModel
import pl.valueadd.restcountries.domain.model.region.RegionalBlocModel

@Parcelize
data class CountryModel(

    var name: String = EMPTY,

    var topLevelDomains: List<String> = emptyList(),

    var alpha2Code: String = EMPTY,

    var alpha3Code: String = EMPTY,

    var callingCodes: List<String> = emptyList(),

    var capital: String = EMPTY,

    var altSpellings: List<String> = emptyList(),

    var region: String = EMPTY,

    var subRegion: String = EMPTY,

    var population: Long = LONG_ZERO,

    var latLng: LatLngModel = LatLngModel(),

    var demonym: String = EMPTY,

    var area: Double = DOUBLE_ZERO,

    var gini: Double = DOUBLE_ZERO,

    var timezones: List<String> = emptyList(),

    var borders: List<CountryFlatModel> = emptyList(),

    var nativeName: String = EMPTY,

    var numericCode: String = EMPTY,

    var currencies: List<CurrencyModel> = emptyList(),

    var languages: List<LanguageModel> = emptyList(),

    var translations: TranslationsModel = TranslationsModel(),

    var flagUrl: String = EMPTY,

    var regionalBlocs: List<RegionalBlocModel> = emptyList(),

    var cioc: String = EMPTY

) : BaseModel(), Parcelable {

    val id: String
        get() = alpha3Code

    val hasBaseInformation: Boolean
        get() = topLevelDomains.isNotEmpty() ||
            callingCodes.isNotEmpty() ||
            timezones.isNotEmpty()
}