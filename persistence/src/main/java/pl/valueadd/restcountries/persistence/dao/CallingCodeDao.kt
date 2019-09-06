package pl.valueadd.restcountries.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity

@Dao
abstract class CallingCodeDao : BaseDao<CallingCodeEntity>() {

    @Query(value = """
        SELECT *
        FROM calling_codes
        WHERE calling_codes.country_id = :countryId
        COLLATE NOCASE
    """)
    abstract fun observeCallingCodes(countryId: String): Flow<List<CallingCodeEntity>>
}