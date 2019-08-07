package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.RegionalBlocDao
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class RegionalBlocPersistenceManager @Inject constructor(private val dao: RegionalBlocDao) {

    fun saveRegionalBlocs(list: List<RegionalBlocEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()
}