package pl.valueadd.restcountries.persistence.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryTopLevelDomainJoin.Companion.COL_COUNTRY_ID
import pl.valueadd.restcountries.persistence.entity.join.CountryTopLevelDomainJoin.Companion.COL_TOP_LEVEL_DOMAIN_ID

@Entity(
    tableName = "country_top_level_domain_join",
    primaryKeys = [
        COL_COUNTRY_ID,
        COL_TOP_LEVEL_DOMAIN_ID
    ],
    foreignKeys = [
        ForeignKey(entity = CountryEntity::class,
            parentColumns = arrayOf("numeric_code"),
            childColumns = arrayOf(COL_COUNTRY_ID),
            onDelete = CASCADE),
        ForeignKey(entity = TopLevelDomainEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf(COL_TOP_LEVEL_DOMAIN_ID),
            onDelete = CASCADE)
    ]
)
data class CountryTopLevelDomainJoin(

    @ColumnInfo(name = COL_COUNTRY_ID)
    var countryId: String = EMPTY,

    @ColumnInfo(name = COL_TOP_LEVEL_DOMAIN_ID)
    var topLevelDomainId: Long = LONG_ZERO

) {

    companion object {
        internal const val COL_COUNTRY_ID = "country_id"
        internal const val COL_TOP_LEVEL_DOMAIN_ID = "top_level_domain_id"
    }
}