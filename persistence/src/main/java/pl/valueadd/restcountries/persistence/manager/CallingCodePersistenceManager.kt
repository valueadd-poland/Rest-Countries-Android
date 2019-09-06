package pl.valueadd.restcountries.persistence.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import pl.valueadd.restcountries.persistence.dao.CallingCodeDao
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallingCodePersistenceManager @Inject constructor(private val dao: CallingCodeDao) {

    suspend fun saveCallingCodesIds(list: List<CallingCodeEntity>): List<Long> =
        dao.insertEntities(list)

    suspend fun saveCallingCodes(list: List<CallingCodeEntity>) =
        dao.insert(list)

    fun observeCallingCodes(countryId: String): Flow<List<CallingCodeEntity>> =
        dao.observeCallingCodes(countryId)
            .distinctUntilChanged()
}