package pl.valueadd.restcountries.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity.Companion.COL_COUNTRY_ID
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity.Companion.COL_NAME
import pl.valueadd.restcountries.persistence.entity.CountryEntity.Companion.COL_ALPHA_3_CODE

@Entity(
    tableName = "calling_codes",
    foreignKeys = [
        ForeignKey(entity = CountryEntity::class,
            parentColumns = arrayOf(COL_ALPHA_3_CODE),
            childColumns = arrayOf(COL_COUNTRY_ID),
            onDelete = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = arrayOf(COL_NAME), unique = true)
    ]
)
data class CallingCodeEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = LONG_ZERO,

    @ColumnInfo(name = COL_NAME)
    var name: String = EMPTY,

    @ColumnInfo(name = COL_COUNTRY_ID)
    var countryId: String = EMPTY

) {

    companion object {
        const val COL_NAME = "name"
        const val COL_COUNTRY_ID = "country_id"
    }
}