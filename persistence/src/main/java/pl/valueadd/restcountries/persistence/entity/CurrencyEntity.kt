package pl.valueadd.restcountries.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.apache.commons.lang3.StringUtils

@Entity(tableName = "currencies")
data class CurrencyEntity(

    @PrimaryKey
    var code: String = StringUtils.EMPTY,

    var name: String = StringUtils.EMPTY,

    var symbol: String = StringUtils.EMPTY
)