package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.CountryDao
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity
import pl.valueadd.restcountries.persistence.entity.BorderEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class CountryPersistenceManager @Inject constructor(private val dao: CountryDao) {

    fun observeAllCountries(): Flowable<List<CountryEntity>> =
        dao.observeAllCountries()
            .subscribeOnIo()

    fun saveCountries(list: List<CountryEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()
}