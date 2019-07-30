package pl.valueadd.restcountries.persistence.manager

import pl.valueadd.restcountries.persistence.dao.ExampleDao
import pl.valueadd.restcountries.persistence.entity.ExampleEntity
import pl.valueadd.restcountries.persistence.preferences.ExampleCache
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.annotations.SchedulerSupport
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class ExamplePersistenceManager @Inject constructor(
    private val dao: ExampleDao,
    private val cache: ExampleCache
) {

    /**
     * Save all entities in database.
     * @return completable stream.
     */
    fun saveExamples(list: List<ExampleEntity>): Completable {
        return dao
            .insert(list)
            .subscribeOnIo()
    }

    /**
     * Observer all examples.
     * @return stream of example list.
     */
    fun observeExample(): Flowable<ExampleEntity> {
        return dao
            .observeExample()
            .subscribeOnIo()
    }

    fun observeAllExamples(): Flowable<List<ExampleEntity>> {
        return dao
            .observeAllExamples()
            .subscribeOnIo()
    }

    fun observeSelectedCountOfExamples(defaultValue: Int): Flowable<Int> =
        cache.observeSelectedCountOfExamples(defaultValue)

    fun setSelectedCountOfExamples(value: Int): Completable =
        cache.setSelectedCountOfExamples(value)
}