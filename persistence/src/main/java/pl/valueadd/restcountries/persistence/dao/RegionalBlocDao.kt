package pl.valueadd.restcountries.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity

@Dao
abstract class RegionalBlocDao : BaseDao<RegionalBlocEntity>() {

    @Query(value = """
        SELECT *
        FROM regional_blocks
        INNER JOIN country_regional_bloc_join
        ON regional_blocks.name = country_regional_bloc_join.regional_bloc_id
        WHERE country_regional_bloc_join.country_id = :countryId
    """)
    abstract fun observeRegionalBlocs(countryId: String): Flow<List<RegionalBlocEntity>>
}