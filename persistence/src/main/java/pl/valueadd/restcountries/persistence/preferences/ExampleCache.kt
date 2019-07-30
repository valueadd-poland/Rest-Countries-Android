package pl.valueadd.restcountries.persistence.preferences

import pl.valueadd.restcountries.utility.reactivex.immediateSingle
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExampleCache @Inject constructor(private val cache: BaseCache) {

    companion object KEY {
        const val SELECTED_COUNT = "SELECTED_COUNT"
    }

    fun observeSelectedCountOfExamples(defaultValue: Int): Flowable<Int> =
        cache.observeInt(SELECTED_COUNT, defaultValue)

    fun getSelectedCountOfExamples(defaultValue: Int): Single<Int> =
        immediateSingle { cache.loadInt(SELECTED_COUNT, defaultValue) }

    fun setSelectedCountOfExamples(value: Int): Completable =
        cache.savePreferenceRx(SELECTED_COUNT, value)
}