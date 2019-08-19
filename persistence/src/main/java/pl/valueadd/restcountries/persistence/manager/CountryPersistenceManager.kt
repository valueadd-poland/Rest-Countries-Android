package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.annotations.SchedulerSupport
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
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class CountryPersistenceManager @Inject constructor(
    private val dao: CountryDao,
    private val currencyDao: CountryCurrencyDao,
    private val languageDao: CountryLanguageDao,
    private val regionalBlocDao: CountryRegionalBlocDao,
    private val timeZoneDao: CountryTimeZoneDao,
    private val borderDao: CountryBorderDao
) {

    fun observeAllCountries(): Flowable<List<CountryEntity>> =
        dao.observeAllCountries()
            .distinctUntilChanged()
            .subscribeOnIo()

    fun observeCountries(countryIds: List<String>): Flowable<List<CountryEntity>> =
        dao.observeCountries(countryIds)
            .distinctUntilChanged()
            .subscribeOnIo()

    fun observeCountriesFlat(countryIds: List<String>): Flowable<List<CountryFlat>> =
        dao.observeCountriesFlat(countryIds)
            .distinctUntilChanged()
            .subscribeOnIo()

    fun observeBorders(countryId: String): Flowable<List<CountryFlat>> =
        dao.observeCountriesFlat(countryId)
            .distinctUntilChanged()
            .subscribeOnIo()

    fun observeCountry(countryId: String): Flowable<CountryEntity> =
        dao.observeCountry(countryId)
            .distinctUntilChanged()
            .subscribeOnIo()

    fun saveCountries(list: List<CountryEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()

    fun saveCountry(entity: CountryEntity): Completable =
        dao.insert(entity)
            .subscribeOnIo()
            .ignoreElement()

    fun saveCountryCurrencyJoins(list: List<CountryCurrencyJoin>): Completable =
        currencyDao.insert(list)
            .subscribeOnIo()

    fun saveCountryLanguageJoins(list: List<CountryLanguageJoin>): Completable =
        languageDao.insert(list)
            .subscribeOnIo()

    fun saveCountryRegionalBlocJoins(list: List<CountryRegionalBlocJoin>): Completable =
        regionalBlocDao.insert(list)
            .subscribeOnIo()

    fun saveCountryTimeZoneJoins(list: List<CountryTimeZoneJoin>): Completable =
        timeZoneDao.insert(list)
            .subscribeOnIo()

    fun saveCountryBorderJoins(list: List<CountryBorderJoin>): Completable =
        borderDao.insert(list)
            .subscribeOnIo()

    fun saveCountryCurrencyJoin(entity: CountryCurrencyJoin): Completable =
        currencyDao.insert(entity)
            .subscribeOnIo()
            .ignoreElement()

    fun saveCountryLanguageJoin(entity: CountryLanguageJoin): Completable =
        languageDao.insert(entity)
            .subscribeOnIo()
            .ignoreElement()

    fun saveCountryRegionalBlocJoin(entity: CountryRegionalBlocJoin): Completable =
        regionalBlocDao.insert(entity)
            .subscribeOnIo()
            .ignoreElement()

    fun saveCountryTimeZoneJoin(entity: CountryTimeZoneJoin): Completable =
        timeZoneDao.insert(entity)
            .subscribeOnIo()
            .ignoreElement()

    fun saveCountryBorderJoin(entity: CountryBorderJoin): Completable =
        borderDao.insert(entity)
            .subscribeOnIo()
            .ignoreElement()
}