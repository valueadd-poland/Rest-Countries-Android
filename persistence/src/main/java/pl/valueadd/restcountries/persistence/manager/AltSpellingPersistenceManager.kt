package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.AltSpellingDao
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class AltSpellingPersistenceManager @Inject constructor(private val dao: AltSpellingDao) {

    fun saveAltSpellings(list: List<AltSpellingEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()

    fun saveAltSpellingsIds(list: List<AltSpellingEntity>): Single<List<Long>> =
        dao.insertEntities(list)
            .subscribeOnIo()
}