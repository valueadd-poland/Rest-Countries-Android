package pl.valueadd.restcountries.persistence.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity.Companion.COL_ALPHA_3_CODE
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryCurrencyJoin.Companion.COL_COUNTRY_ID
import pl.valueadd.restcountries.persistence.entity.join.CountryCurrencyJoin.Companion.COL_CURRENCY_ID

@Entity(
    tableName = "country_currency_join",
    primaryKeys = [
        COL_COUNTRY_ID,
        COL_CURRENCY_ID
    ],
    foreignKeys = [
        ForeignKey(entity = CountryEntity::class,
            parentColumns = arrayOf(COL_ALPHA_3_CODE),
            childColumns = arrayOf(COL_COUNTRY_ID),
            onDelete = CASCADE),
        ForeignKey(entity = CurrencyEntity::class,
            parentColumns = arrayOf("code"),
            childColumns = arrayOf(COL_CURRENCY_ID),
            onDelete = CASCADE)
    ]
)
data class CountryCurrencyJoin(

    @ColumnInfo(name = COL_COUNTRY_ID)
    var countryId: String = EMPTY,

    @ColumnInfo(name = COL_CURRENCY_ID)
    var currencyId: String = EMPTY

) {
    companion object {
        internal const val COL_COUNTRY_ID = "country_id"
        internal const val COL_CURRENCY_ID = "currency_id"
    }
}