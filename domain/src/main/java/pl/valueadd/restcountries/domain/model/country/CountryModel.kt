package pl.valueadd.restcountries.domain.model.country

import android.os.Parcel
import android.os.Parcelable
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO
import pl.valueadd.restcountries.domain.model.base.BaseModel
import pl.valueadd.restcountries.domain.model.currency.CurrencyModel
import pl.valueadd.restcountries.domain.model.language.LanguageModel
import pl.valueadd.restcountries.domain.model.language.TranslationsModel
import pl.valueadd.restcountries.domain.model.region.RegionalBlocModel
import pl.valueadd.restcountries.utility.parcel.createStringList
import pl.valueadd.restcountries.utility.parcel.loadString

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

    constructor(parcel: Parcel) : this(
        parcel.loadString(),
        parcel.createStringList(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.createStringList(),
        parcel.loadString(),
        parcel.createStringList(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.readLong(),
        parcel.readParcelable(LatLngModel::class.java.classLoader),
        parcel.loadString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.createStringList(),
        parcel.createTypedArrayList(CountryFlatModel),
        parcel.loadString(),
        parcel.loadString(),
        parcel.createTypedArrayList(CurrencyModel),
        parcel.createTypedArrayList(LanguageModel),
        parcel.readParcelable(TranslationsModel::class.java.classLoader),
        parcel.loadString(),
        parcel.createTypedArrayList(RegionalBlocModel),
        parcel.loadString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeStringList(topLevelDomains)
        parcel.writeString(alpha2Code)
        parcel.writeString(alpha3Code)
        parcel.writeStringList(callingCodes)
        parcel.writeString(capital)
        parcel.writeStringList(altSpellings)
        parcel.writeString(region)
        parcel.writeString(subRegion)
        parcel.writeLong(population)
        parcel.writeParcelable(latLng, flags)
        parcel.writeString(demonym)
        parcel.writeDouble(area)
        parcel.writeDouble(gini)
        parcel.writeStringList(timezones)
        parcel.writeTypedList(borders)
        parcel.writeString(nativeName)
        parcel.writeString(numericCode)
        parcel.writeTypedList(currencies)
        parcel.writeTypedList(languages)
        parcel.writeParcelable(translations, flags)
        parcel.writeString(flagUrl)
        parcel.writeTypedList(regionalBlocs)
        parcel.writeString(cioc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryModel> {
        override fun createFromParcel(parcel: Parcel): CountryModel {
            return CountryModel(parcel)
        }

        override fun newArray(size: Int): Array<CountryModel?> {
            return arrayOfNulls(size)
        }
    }
}