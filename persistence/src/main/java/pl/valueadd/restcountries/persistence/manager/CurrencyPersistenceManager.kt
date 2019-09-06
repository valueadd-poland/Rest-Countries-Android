package pl.valueadd.restcountries.persistence.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import pl.valueadd.restcountries.persistence.dao.CurrencyDao
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyPersistenceManager @Inject constructor(private val dao: CurrencyDao) {

    suspend fun saveCurrencies(list: List<CurrencyEntity>) {
        dao.insert(list)
    }

    suspend fun saveCurrenciesIds(list: List<CurrencyEntity>): List<Long> =
        dao.insertEntities(list)

    fun observeCurrencies(countryId: String): Flow<List<CurrencyEntity>> =
        dao.observeCurriences(countryId)
            .distinctUntilChanged()
}