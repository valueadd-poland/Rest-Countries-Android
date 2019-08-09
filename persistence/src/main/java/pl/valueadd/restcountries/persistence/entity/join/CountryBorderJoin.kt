package pl.valueadd.restcountries.persistence.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO
import pl.valueadd.restcountries.persistence.entity.BorderEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryBorderJoin.Companion.COL_BORDER_ID
import pl.valueadd.restcountries.persistence.entity.join.CountryBorderJoin.Companion.COL_COUNTRY_ID

@Entity(
    tableName = "country_border_join",
    primaryKeys = [
        COL_COUNTRY_ID,
        COL_BORDER_ID
    ],
    foreignKeys = [
        ForeignKey(entity = CountryEntity::class,
            parentColumns = arrayOf("numeric_code"),
            childColumns = arrayOf(COL_COUNTRY_ID),
            onDelete = CASCADE),
        ForeignKey(entity = BorderEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf(COL_BORDER_ID),
            onDelete = CASCADE)
    ]
)
data class CountryBorderJoin(

    @ColumnInfo(name = COL_COUNTRY_ID)
    var countryId: String = EMPTY,

    @ColumnInfo(name = COL_BORDER_ID)
    var borderId: Long = LONG_ZERO

) {
    companion object {
        internal const val COL_COUNTRY_ID = "country_id"
        internal const val COL_BORDER_ID = "border_id"
    }
}