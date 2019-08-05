package pl.valueadd.restcountries.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils

@Entity(tableName = "countries")
data class CountryEntity (

    @PrimaryKey
    @ColumnInfo(name = "numericCode")
    var numericCode: String = StringUtils.EMPTY,

    @ColumnInfo(name = "name")
    var name: String = StringUtils.EMPTY,

    @ColumnInfo(name = "alpha2Code")
    var alpha2Code: String = StringUtils.EMPTY,

    @ColumnInfo(name = "alpha3Code")
    var alpha3Code: String = StringUtils.EMPTY,

    @ColumnInfo(name = "capital")
    var capital: String = StringUtils.EMPTY,

//    @ColumnInfo(name = "altSpellings")
//    var altSpellings: List<String> = emptyList(),

    @ColumnInfo(name = "region")
    var region: String = StringUtils.EMPTY,

    @ColumnInfo(name = "subRegion")
    var subRegion: String = StringUtils.EMPTY,

    @ColumnInfo(name = "population")
    var population: Long = NumberUtils.LONG_ZERO,

    @Embedded
    var latLng: LatLngEntity = LatLngEntity(),

    @ColumnInfo(name = "demonym")
    var demonym: String = StringUtils.EMPTY,

    @ColumnInfo(name = "area")
    var area: Double = NumberUtils.DOUBLE_ZERO,

    @ColumnInfo(name = "gini")
    var gini: Double = NumberUtils.DOUBLE_ZERO,

//    @ColumnInfo(name = "timezones")
//    var timezones: List<String> = emptyList(),
//
//    @ColumnInfo(name = "borders")
//    var borders: List<String> = emptyList(),

    @ColumnInfo(name = "nativeName")
    var nativeName: String = StringUtils.EMPTY,

    @Embedded
    var translations: TranslationsEntity = TranslationsEntity(),

    @ColumnInfo(name = "flag")
    var flag: String = StringUtils.EMPTY,

//    @ColumnInfo(name = "regionalBlocs")
//    var regionalBlocs: List<String> = emptyList(),

    @ColumnInfo(name = "cioc")
    var cioc: String = StringUtils.EMPTY
)