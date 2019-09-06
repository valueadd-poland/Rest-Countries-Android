package pl.valueadd.restcountries.persistence.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import pl.valueadd.restcountries.persistence.dao.TopLevelDomainDao
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopLevelDomainPersistenceManager @Inject constructor(private val dao: TopLevelDomainDao) {

    suspend fun saveTopLevelDomains(list: List<TopLevelDomainEntity>) =
        dao.insert(list)

    suspend fun saveTopLevelDomainsIds(list: List<TopLevelDomainEntity>): List<Long> =
        dao.insertEntities(list)

    fun observeTopLevelDomains(countryId: String): Flow<List<TopLevelDomainEntity>> =
        dao.observeTopLevelDomains(countryId)
            .distinctUntilChanged()
}