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
}