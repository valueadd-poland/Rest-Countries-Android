package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.CurrencyDao
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class CurrencyPersistenceManager @Inject constructor(private val dao: CurrencyDao) {

    fun saveCurrencies(list: List<CurrencyEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()

    fun saveCurrenciesIds(list: List<CurrencyEntity>): Single<List<Long>> =
        dao.insertEntities(list)
            .subscribeOnIo()

    fun observeCurriencies(countryId: String): Flowable<List<CurrencyEntity>> =
        dao.observeCurriences(countryId)
            .distinctUntilChanged()
            .subscribeOnIo()
}