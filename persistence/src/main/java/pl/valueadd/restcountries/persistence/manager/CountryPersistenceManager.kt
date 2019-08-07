package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.CountryCallingCodeDao
import pl.valueadd.restcountries.persistence.dao.CountryCurrencyDao
import pl.valueadd.restcountries.persistence.dao.CountryDao
import pl.valueadd.restcountries.persistence.dao.CountryLanguageDao
import pl.valueadd.restcountries.persistence.dao.CountryTopLevelDomainDao
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryCallingCodeJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryCurrencyJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryLanguageJoin
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
                                                    private val topLevelDomainDao: CountryTopLevelDomainDao) {

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
}