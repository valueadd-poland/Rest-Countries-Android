package pl.valueadd.restcountries.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.model.CountryFlat

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

    @Query(value = """
        SELECT name, alpha3_code, alpha2_code, flag_url
        FROM countries
        WHERE countries.alpha3_code IN (:countryIds)
        ORDER BY name COLLATE NOCASE ASC
    """)
    abstract fun observeCountriesFlat(countryIds: List<String>): Flowable<List<CountryFlat>>

    @Query(value = """
        SELECT name, alpha3_code, alpha2_code, flag_url
        FROM countries
        INNER JOIN country_border_join
        ON countries.alpha3_code = country_border_join.border_id
        WHERE country_border_join.country_id = :countryId
    """)
    abstract fun observeCountriesFlat(countryId: String): Flowable<List<CountryFlat>>

    @Query("""
        SELECT *
        FROM countries
        WHERE lower(name) LIKE '%' || :query || '%'
        OR lower(alpha3_code) LIKE '%' || :query || '%'
        OR lower(alpha2_code) LIKE '%' || :query || '%'
        OR lower(numeric_code) LIKE '%' || :query || '%'
        COLLATE NOCASE
        ORDER BY 
            CASE WHEN :ascendingOrder = 1 THEN name END ASC,
            CASE WHEN :ascendingOrder = 0 THEN name END DESC
    """)
    abstract fun observeCountries(query: String, ascendingOrder: Boolean): Flowable<List<CountryEntity>>
}