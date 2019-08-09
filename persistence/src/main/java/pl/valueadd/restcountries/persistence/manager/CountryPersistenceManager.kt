package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.CountryAltSpellingDao
import pl.valueadd.restcountries.persistence.dao.CountryBorderDao
import pl.valueadd.restcountries.persistence.dao.CountryCallingCodeDao
import pl.valueadd.restcountries.persistence.dao.CountryCurrencyDao
import pl.valueadd.restcountries.persistence.dao.CountryDao
import pl.valueadd.restcountries.persistence.dao.CountryLanguageDao
import pl.valueadd.restcountries.persistence.dao.CountryRegionalBlocDao
import pl.valueadd.restcountries.persistence.dao.CountryTimeZoneDao
import pl.valueadd.restcountries.persistence.dao.CountryTopLevelDomainDao
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryAltSpellingJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryBorderJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryCallingCodeJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryCurrencyJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryLanguageJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryRegionalBlocJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryTimeZoneJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryTopLevelDomainJoin
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class CountryPersistenceManager @Inject constructor(private val dao: CountryDao,
                                                    private val callingCodeDao: CountryCallingCodeDao,
                                                    private val currencyDao: CountryCurrencyDao,
                                                    private val languageDao: CountryLanguageDao,
                                                    private val topLevelDomainDao: CountryTopLevelDomainDao,
                                                    private val altSpellingDao: CountryAltSpellingDao,
                                                    private val regionalBlocDao: CountryRegionalBlocDao,
                                                    private val timeZoneDao: CountryTimeZoneDao,
                                                    private val borderDao: CountryBorderDao) {

    fun observeAllCountries(): Flowable<List<CountryEntity>> =
        dao.observeAllCountries()
            .distinctUntilChanged()
            .subscribeOnIo()

    fun saveCountries(list: List<CountryEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()

    fun saveCountryCallingCodeJoins(list: List<CountryCallingCodeJoin>): Completable =
        callingCodeDao.insert(list)
            .subscribeOnIo()

    fun saveCountryCurrencyJoins(list: List<CountryCurrencyJoin>): Completable =
        currencyDao.insert(list)
            .subscribeOnIo()

    fun saveCountryLanguageJoins(list: List<CountryLanguageJoin>): Completable =
        languageDao.insert(list)
            .subscribeOnIo()

    fun saveCountryTopLevelDomainJoins(list: List<CountryTopLevelDomainJoin>): Completable =
        topLevelDomainDao.insert(list)
            .subscribeOnIo()

    fun saveCountryAltSpellingJoins(list: List<CountryAltSpellingJoin>): Completable =
        altSpellingDao.insert(list)
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

    fun saveCountryCallingCodeJoin(entity: CountryCallingCodeJoin): Completable =
        callingCodeDao.insert(entity)
            .subscribeOnIo()
            .ignoreElement()

    fun saveCountryCurrencyJoin(entity: CountryCurrencyJoin): Completable =
        currencyDao.insert(entity)
            .subscribeOnIo()
            .ignoreElement()

    fun saveCountryLanguageJoin(entity: CountryLanguageJoin): Completable =
        languageDao.insert(entity)
            .subscribeOnIo()
            .ignoreElement()

    fun saveCountryTopLevelDomainJoin(entity: CountryTopLevelDomainJoin): Completable =
        topLevelDomainDao.insert(entity)
            .subscribeOnIo()
            .ignoreElement()

    fun saveCountryAltSpellingJoin(entity: CountryAltSpellingJoin): Completable =
        altSpellingDao.insert(entity)
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