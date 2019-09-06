package pl.valueadd.restcountries.persistence.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.apache.commons.lang3.StringUtils.stripAccents
import pl.valueadd.restcountries.persistence.dao.CountryBorderDao
import pl.valueadd.restcountries.persistence.dao.CountryCurrencyDao
import pl.valueadd.restcountries.persistence.dao.CountryDao
import pl.valueadd.restcountries.persistence.dao.CountryLanguageDao
import pl.valueadd.restcountries.persistence.dao.CountryRegionalBlocDao
import pl.valueadd.restcountries.persistence.dao.CountryTimeZoneDao
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryBorderJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryCurrencyJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryLanguageJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryRegionalBlocJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryTimeZoneJoin
import pl.valueadd.restcountries.persistence.model.CountryFlat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryPersistenceManager @Inject constructor(
    private val dao: CountryDao,
    private val currencyDao: CountryCurrencyDao,
    private val languageDao: CountryLanguageDao,
    private val regionalBlocDao: CountryRegionalBlocDao,
    private val timeZoneDao: CountryTimeZoneDao,
    private val borderDao: CountryBorderDao
) {

    fun observeAllCountries(): Flow<List<CountryEntity>> =
        dao.observeAllCountries()
            .distinctUntilChanged()

    fun observeCountries(countryIds: List<String>): Flow<List<CountryEntity>> =
        dao.observeCountries(countryIds)
            .distinctUntilChanged()

    fun observeCountries(query: String, ascendingOrder: Boolean = true): Flow<List<CountryEntity>> =
        dao.observeCountries(stripAccents(query), ascendingOrder)
            .distinctUntilChanged()

    fun observeCountriesFlat(countryIds: List<String>): Flow<List<CountryFlat>> =
        dao.observeCountriesFlat(countryIds)
            .distinctUntilChanged()

    fun observeBorders(countryId: String): Flow<List<CountryFlat>> =
        dao.observeCountriesFlat(countryId)
            .distinctUntilChanged()

    fun observeCountry(countryId: String): Flow<CountryEntity> =
        dao.observeCountry(countryId)
            .distinctUntilChanged()

    suspend fun saveCountries(list: List<CountryEntity>) =
        dao.insert(list)

    suspend fun saveCountry(entity: CountryEntity) =
        dao.insert(entity)

    suspend fun saveCountryCurrencyJoins(list: List<CountryCurrencyJoin>) =
        currencyDao.insert(list)

    suspend fun saveCountryLanguageJoins(list: List<CountryLanguageJoin>) =
        languageDao.insert(list)

    suspend fun saveCountryRegionalBlocJoins(list: List<CountryRegionalBlocJoin>) =
        regionalBlocDao.insert(list)

    suspend fun saveCountryTimeZoneJoins(list: List<CountryTimeZoneJoin>) =
        timeZoneDao.insert(list)

    suspend fun saveCountryBorderJoins(list: List<CountryBorderJoin>) =
        borderDao.insert(list)

    suspend fun saveCountryCurrencyJoin(entity: CountryCurrencyJoin) =
        currencyDao.insert(entity)

    suspend fun saveCountryLanguageJoin(entity: CountryLanguageJoin) =
        languageDao.insert(entity)

    suspend fun saveCountryRegionalBlocJoin(entity: CountryRegionalBlocJoin) =
        regionalBlocDao.insert(entity)

    suspend fun saveCountryTimeZoneJoin(entity: CountryTimeZoneJoin) =
        timeZoneDao.insert(entity)

    suspend fun saveCountryBorderJoin(entity: CountryBorderJoin) =
        borderDao.insert(entity)
}