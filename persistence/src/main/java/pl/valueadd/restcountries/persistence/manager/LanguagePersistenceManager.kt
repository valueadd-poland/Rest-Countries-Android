package pl.valueadd.restcountries.persistence.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.annotations.SchedulerSupport
import pl.valueadd.restcountries.persistence.dao.LanguageDao
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class LanguagePersistenceManager @Inject constructor(private val dao: LanguageDao) {

    fun saveLanguages(list: List<LanguageEntity>): Completable =
        dao.insert(list)
            .subscribeOnIo()

    fun saveLanguagesIds(list: List<LanguageEntity>): Single<List<Long>> =
        dao.insertEntities(list)
            .subscribeOnIo()

    fun observeLanguages(countryId: String): Flowable<List<LanguageEntity>> =
        dao.observeLanguages(countryId)
            .distinctUntilChanged()
            .subscribeOnIo()
}