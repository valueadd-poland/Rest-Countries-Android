package pl.valueadd.restcountries.persistence.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryCallingCodeJoin.Companion.COL_CALLING_CODE_ID
import pl.valueadd.restcountries.persistence.entity.join.CountryCallingCodeJoin.Companion.COL_COUNTRY_ID

@Entity(
    tableName = "country_calling_code_join",
    primaryKeys = [
        COL_COUNTRY_ID,
        COL_CALLING_CODE_ID
    ],
    foreignKeys = [
        ForeignKey(entity = CountryEntity::class,
            parentColumns = arrayOf("numeric_code"),
            childColumns = arrayOf(COL_COUNTRY_ID),
            onDelete = CASCADE),
        ForeignKey(entity = CallingCodeEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf(COL_CALLING_CODE_ID),
            onDelete = CASCADE)
    ]
)
data class CountryCallingCodeJoin(

    @ColumnInfo(name = COL_COUNTRY_ID)
    var countryId: String = EMPTY,

    @ColumnInfo(name = COL_CALLING_CODE_ID)
    var collingCodeId: Long = LONG_ZERO

) {
    companion object {
        internal const val COL_COUNTRY_ID = "country_id"
        internal const val COL_CALLING_CODE_ID = "calling_code_id"
    }
}