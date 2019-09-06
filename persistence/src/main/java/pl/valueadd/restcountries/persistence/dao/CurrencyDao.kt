package pl.valueadd.restcountries.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity

@Dao
abstract class CurrencyDao : BaseDao<CurrencyEntity>() {

    @Query(value = """
        SELECT *
        FROM currencies
        INNER JOIN country_currency_join
        ON currencies.code = country_currency_join.currency_id
        WHERE country_currency_join.country_id = :countryId
    """)
    abstract fun observeCurriences(countryId: String): Flow<List<CurrencyEntity>>
}