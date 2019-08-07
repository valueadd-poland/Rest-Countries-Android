package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.BorderDao
import pl.valueadd.restcountries.persistence.entity.BorderEntity
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class BorderPersistenceManager @Inject constructor(private val dao: BorderDao) {

    fun saveBorders(list: List<BorderEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()
}