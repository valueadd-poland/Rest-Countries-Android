package pl.valueadd.restcountries.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.valueadd.restcountries.persistence.entity.LanguageEntity

@Dao
abstract class LanguageDao : BaseDao<LanguageEntity>() {

    @Query(value = """
        SELECT *
        FROM languages
        INNER JOIN country_language_join
        ON languages.iso6392 = country_language_join.language_id
        WHERE country_language_join.country_id = :countryId
    """)
    abstract fun observeLanguages(countryId: String): Flow<List<LanguageEntity>>
}