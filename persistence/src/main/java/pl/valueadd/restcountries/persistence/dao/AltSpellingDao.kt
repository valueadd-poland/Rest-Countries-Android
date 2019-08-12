package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity

@Dao
abstract class AltSpellingDao : BaseDao<AltSpellingEntity>() {

    @Query(value = """
        SELECT *
        FROM alt_spellings
        INNER JOIN country_alt_spelling_join
        ON alt_spellings.id = country_alt_spelling_join.alt_spelling_id
        WHERE country_alt_spelling_join.country_id = :countryId
    """)
    abstract fun observeAltSpellings(countryId: String): Flowable<List<AltSpellingEntity>>
}