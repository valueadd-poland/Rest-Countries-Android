package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.CallingCodeDao
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class CallingCodePersistenceManager @Inject constructor(private val dao: CallingCodeDao) {

    fun saveCallingCodesIds(list: List<CallingCodeEntity>): Single<List<Long>> =
        dao.insertEntities(list)
            .subscribeOnIo()

    fun saveCallingCodes(list: List<CallingCodeEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()

    fun observeCallingCodes(countryId: String): Flowable<List<CallingCodeEntity>> =
        dao.observeCallingCodes(countryId)
            .distinctUntilChanged()
            .subscribeOnIo()
}