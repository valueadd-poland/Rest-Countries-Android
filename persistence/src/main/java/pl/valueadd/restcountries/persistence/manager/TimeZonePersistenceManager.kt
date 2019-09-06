package pl.valueadd.restcountries.persistence.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import pl.valueadd.restcountries.persistence.dao.TimeZoneDao
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeZonePersistenceManager @Inject constructor(private val dao: TimeZoneDao) {

    suspend fun saveTimezonesIds(list: List<TimeZoneEntity>): List<Long> =
        dao.insertEntities(list)

    suspend fun saveTimezones(list: List<TimeZoneEntity>) =
        dao.insert(list)

    fun observeTimeZones(countryId: String): Flow<List<TimeZoneEntity>> =
        dao.observeTimeZones(countryId)
            .distinctUntilChanged()
}