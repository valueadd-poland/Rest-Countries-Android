package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.TimeZoneDao
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class TimeZonePersistenceManager @Inject constructor(private val dao: TimeZoneDao) {

    fun saveTimezones(list: List<TimeZoneEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()
}