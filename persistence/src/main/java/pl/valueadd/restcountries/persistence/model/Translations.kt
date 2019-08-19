package pl.valueadd.restcountries.persistence.model

import androidx.room.ColumnInfo
import org.apache.commons.lang3.StringUtils.EMPTY

data class Translations(

    @ColumnInfo(name = "de")
    var de: String = EMPTY,

    @ColumnInfo(name = "es")
    var es: String = EMPTY,

    @ColumnInfo(name = "fr")
    var fr: String = EMPTY,

    @ColumnInfo(name = "ja")
    var ja: String = EMPTY,

    @ColumnInfo(name = "it")
    var it: String = EMPTY,

    @ColumnInfo(name = "br")
    var br: String = EMPTY,

    @ColumnInfo(name = "pt")
    var pt: String = EMPTY,

    @ColumnInfo(name = "nl")
    var nl: String = EMPTY,

    @ColumnInfo(name = "hr")
    var hr: String = EMPTY,

    @ColumnInfo(name = "fa")
    var fa: String = EMPTY

)