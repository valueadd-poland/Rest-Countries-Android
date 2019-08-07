package pl.valueadd.restcountries.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.apache.commons.lang3.StringUtils

@Entity(tableName = "currencies")
data class CurrencyEntity(

    @PrimaryKey
    @ColumnInfo(name = "code")
    var code: String = StringUtils.EMPTY,

    @ColumnInfo(name = "name")
    var name: String = StringUtils.EMPTY,

    @ColumnInfo(name = "symbol")
    var symbol: String = StringUtils.EMPTY
) {

    var id: String
        get() = code
        set(value) {
            code = value
        }
}