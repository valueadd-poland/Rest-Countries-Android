package pl.valueadd.restcountries.persistence.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity.Companion.COL_ALPHA_3_CODE
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryTimeZoneJoin.Companion.COL_COUNTRY_ID
import pl.valueadd.restcountries.persistence.entity.join.CountryTimeZoneJoin.Companion.COL_TIME_ZONE_ID

@Entity(
    tableName = "country_time_zone_join",
    primaryKeys = [
        COL_COUNTRY_ID,
        COL_TIME_ZONE_ID
    ],
    foreignKeys = [
        ForeignKey(entity = CountryEntity::class,
            parentColumns = arrayOf(COL_ALPHA_3_CODE),
            childColumns = arrayOf(COL_COUNTRY_ID),
            onDelete = CASCADE),
        ForeignKey(entity = TimeZoneEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf(COL_TIME_ZONE_ID),
            onDelete = CASCADE)
    ]
)
data class CountryTimeZoneJoin(

    @ColumnInfo(name = COL_COUNTRY_ID)
    var countryId: String = EMPTY,

    @ColumnInfo(name = COL_TIME_ZONE_ID)
    var timeZoneId: Long = LONG_ZERO

) {

    companion object {
        internal const val COL_COUNTRY_ID = "country_id"
        internal const val COL_TIME_ZONE_ID = "time_zone_id"
    }
}