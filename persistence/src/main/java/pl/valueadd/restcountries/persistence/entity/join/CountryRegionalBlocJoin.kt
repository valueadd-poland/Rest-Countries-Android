package pl.valueadd.restcountries.persistence.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryRegionalBlocJoin.Companion.COL_COUNTRY_ID
import pl.valueadd.restcountries.persistence.entity.join.CountryRegionalBlocJoin.Companion.COL_REGIONAL_BLOC_ID

@Entity(
    tableName = "country_regional_bloc_join",
    primaryKeys = [
        COL_COUNTRY_ID,
        COL_REGIONAL_BLOC_ID
    ],
    foreignKeys = [
        ForeignKey(entity = CountryEntity::class,
            parentColumns = arrayOf("numeric_code"),
            childColumns = arrayOf(COL_COUNTRY_ID),
            onDelete = CASCADE),
        ForeignKey(entity = RegionalBlocEntity::class,
            parentColumns = arrayOf("name"),
            childColumns = arrayOf(COL_REGIONAL_BLOC_ID),
            onDelete = CASCADE)
    ]
)
data class CountryRegionalBlocJoin(

    @ColumnInfo(name = COL_COUNTRY_ID)
    var countryId: String = EMPTY,

    @ColumnInfo(name = COL_REGIONAL_BLOC_ID)
    var regionalBlocId: String = EMPTY

) {
    companion object {
        internal const val COL_COUNTRY_ID = "country_id"
        internal const val COL_REGIONAL_BLOC_ID = "regional_bloc_id"
    }
}