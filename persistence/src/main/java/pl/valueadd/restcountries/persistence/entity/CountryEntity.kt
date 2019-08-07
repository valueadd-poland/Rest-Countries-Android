package pl.valueadd.restcountries.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils
import pl.valueadd.restcountries.persistence.model.LatLng
import pl.valueadd.restcountries.persistence.model.Translations

@Entity(tableName = "countries")
data class CountryEntity(

    @PrimaryKey
    @ColumnInfo(name = "numeric_code")
    var numericCode: String = EMPTY,

    @ColumnInfo(name = "name")
    var name: String = EMPTY,

    @ColumnInfo(name = "alpha2_code")
    var alpha2Code: String = EMPTY,

    @ColumnInfo(name = "alpha3_code")
    var alpha3Code: String = EMPTY,

    @ColumnInfo(name = "capital")
    var capital: String = EMPTY,

//    @ColumnInfo(name = "altSpellings")
//    var altSpellings: List<String> = emptyList(),

    @ColumnInfo(name = "region")
    var region: String = EMPTY,

    @ColumnInfo(name = "sub_region")
    var subRegion: String = EMPTY,

    @ColumnInfo(name = "population")
    var population: Long = NumberUtils.LONG_ZERO,

    @Embedded
    var latLng: LatLng = LatLng(),

    @ColumnInfo(name = "demonym")
    var demonym: String = EMPTY,

    @ColumnInfo(name = "area")
    var area: Double = NumberUtils.DOUBLE_ZERO,

    @ColumnInfo(name = "gini")
    var gini: Double = NumberUtils.DOUBLE_ZERO,

//    @ColumnInfo(name = "timezones")
//    var timezones: List<String> = emptyList(),
//
//    @ColumnInfo(name = "borders")
//    var borders: List<String> = emptyList(),

    @ColumnInfo(name = "native_name")
    var nativeName: String = EMPTY,

    @Embedded(prefix = "translation_")
    var translations: Translations = Translations(),

    @ColumnInfo(name = "flag")
    var flag: String = EMPTY,

//    @ColumnInfo(name = "regionalBlocs")
//    var regionalBlocs: List<String> = emptyList(),

    @ColumnInfo(name = "cioc")
    var cioc: String = EMPTY
) {

    var id: String
        get() = numericCode
        set(value) {
            numericCode = value
        }
}