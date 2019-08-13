package pl.valueadd.restcountries.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import pl.valueadd.restcountries.persistence.entity.CountryEntity

@Dao
abstract class CountryDao : BaseDao<CountryEntity>() {

    @Query(value = """
        SELECT *
        FROM countries
    """)
    abstract fun observeAllCountries(): Flowable<List<CountryEntity>>

    @Query(value = """
        SELECT *
        FROM countries
        WHERE countries.alpha3_code = :countryId
        COLLATE NOCASE
        LIMIT 1
    """)
    abstract fun observeCountry(countryId: String): Flowable<CountryEntity>

    @Query(value = """
        SELECT *
        FROM countries
        WHERE countries.alpha3_code IN (:countryIds)
        ORDER BY name COLLATE NOCASE ASC
    """)
    abstract fun observeCountries(countryIds: List<String>): Flowable<List<CountryEntity>>
}