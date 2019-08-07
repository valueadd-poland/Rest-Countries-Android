package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.TimeZoneDao
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class TimeZonePersistenceManager @Inject constructor(private val dao: TimeZoneDao) {

    fun saveTimezonesIds(list: List<TimeZoneEntity>): Single<List<Long>> =
        dao.insertEntities(list)
            .subscribeOnIo()

    fun saveTimezones(list: List<TimeZoneEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()
}