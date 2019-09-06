package pl.valueadd.restcountries.persistence.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import pl.valueadd.restcountries.persistence.dao.RegionalBlocDao
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegionalBlocPersistenceManager @Inject constructor(private val dao: RegionalBlocDao) {

    suspend fun saveRegionalBlocs(list: List<RegionalBlocEntity>) =
        dao.insert(list)

    fun observeRegionalBlocs(countryId: String): Flow<List<RegionalBlocEntity>> =
        dao.observeRegionalBlocs(countryId)
            .distinctUntilChanged()
}