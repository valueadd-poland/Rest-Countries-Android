package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.TopLevelDomainDao
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class TopLevelDomainPersistenceManager @Inject constructor(private val dao: TopLevelDomainDao) {

    fun saveTopLevelDomains(list: List<TopLevelDomainEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()

    fun saveTopLevelDomainsIds(list: List<TopLevelDomainEntity>): Single<List<Long>> =
        dao.insertEntities(list)
            .subscribeOnIo()

    fun observeTopLevelDomains(countryId: String): Flowable<List<TopLevelDomainEntity>> =
        dao.observeTopLevelDomains(countryId)
            .distinctUntilChanged()
            .subscribeOnIo()
}