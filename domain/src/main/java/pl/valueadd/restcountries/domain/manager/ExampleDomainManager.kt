package pl.valueadd.restcountries.domain.manager

import pl.valueadd.restcountries.domain.mapper.ExampleMapper
import pl.valueadd.restcountries.domain.model.ExampleModel
import pl.valueadd.restcountries.network.manager.ExampleNetworkManager
import pl.valueadd.restcountries.persistence.manager.ExamplePersistenceManager
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExampleDomainManager @Inject constructor(
    private val persistence: ExamplePersistenceManager,
    private val network: ExampleNetworkManager,
    private val mapper: ExampleMapper
) {

    fun downloadExamples(): Completable =
        network
            .getExamples()
            .map(mapper::mapExampleDtosToEntities)
            .flatMapCompletable {
                persistence.saveExamples(it)
            }

    fun observeFirstExample(): Flowable<ExampleModel> =
        persistence
            .observeExample()
            .map(mapper::mapExampleEntityToModel)

    fun downloadMockedExamples(): Completable =
        network
            .getMockedExamples()
            .map(mapper::mapExampleDtosToEntities)
            .flatMapCompletable {
                persistence.saveExamples(it)
            }

    fun observeAllExamples(): Flowable<List<ExampleModel>> =
        persistence
            .observeAllExamples()
            .map(mapper::mapExampleEntitiesToModels)

    fun observeSelectedCountOfExamples(defaultValue: Int): Flowable<Int> =
        persistence
            .observeSelectedCountOfExamples(defaultValue)

    fun setSelectedCountOfExamples(value: Int): Completable =
        persistence
            .setSelectedCountOfExamples(value)
}