package pl.valueadd.restcountries.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.apache.commons.lang3.StringUtils

@Entity(tableName = "languages")
data class LanguageEntity(

    @PrimaryKey
    @ColumnInfo(name = "iso6392")
    var iso6392: String = StringUtils.EMPTY,

    @ColumnInfo(name = "iso6391")
    var iso6391: String = StringUtils.EMPTY,

    @ColumnInfo(name = "name")
    var name: String = StringUtils.EMPTY,

    @ColumnInfo(name = "native_name")
    var nativeName: String = StringUtils.EMPTY

) {

    var id: String
        get() = iso6392
        set(value) {
            iso6392 = value
        }
}