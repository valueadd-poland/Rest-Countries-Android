package pl.valueadd.restcountries.persistence.entity.join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryLanguageJoin.Companion.COL_COUNTRY_ID
import pl.valueadd.restcountries.persistence.entity.join.CountryLanguageJoin.Companion.COL_LANGUAGE_ID

@Entity(
    tableName = "country_language_join",
    primaryKeys = [
        COL_COUNTRY_ID,
        COL_LANGUAGE_ID
    ],
    foreignKeys = [
        ForeignKey(entity = CountryEntity::class,
            parentColumns = arrayOf("alpha3_code"),
            childColumns = arrayOf(COL_COUNTRY_ID),
            onDelete = CASCADE),
        ForeignKey(entity = LanguageEntity::class,
            parentColumns = arrayOf("iso6392"),
            childColumns = arrayOf(COL_LANGUAGE_ID),
            onDelete = CASCADE)
    ]
)
data class CountryLanguageJoin(

    @ColumnInfo(name = COL_COUNTRY_ID)
    var countryId: String = EMPTY,

    @ColumnInfo(name = COL_LANGUAGE_ID)
    var languageId: String = EMPTY

) {

    companion object {
        internal const val COL_COUNTRY_ID = "country_id"
        internal const val COL_LANGUAGE_ID = "language_id"
    }
}