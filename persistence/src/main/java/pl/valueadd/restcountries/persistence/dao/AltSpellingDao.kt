package pl.valueadd.restcountries.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity

@Dao
abstract class AltSpellingDao : BaseDao<AltSpellingEntity>() {

    @Query(value = """
        SELECT *
        FROM alt_spellings
        WHERE alt_spellings.country_id = :countryId
        COLLATE NOCASE
    """)
    abstract fun observeAltSpellings(countryId: String): Flow<List<AltSpellingEntity>>
}