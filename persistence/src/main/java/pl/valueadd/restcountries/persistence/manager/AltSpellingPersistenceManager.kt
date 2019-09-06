package pl.valueadd.restcountries.persistence.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import pl.valueadd.restcountries.persistence.dao.AltSpellingDao
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AltSpellingPersistenceManager @Inject constructor(private val dao: AltSpellingDao) {

    suspend fun saveAltSpellings(list: List<AltSpellingEntity>) =
        dao.insert(list)

    suspend fun saveAltSpellingsIds(list: List<AltSpellingEntity>): List<Long> =
        dao.insertEntities(list)

    fun observeAltSpellings(countryId: String): Flow<List<AltSpellingEntity>> =
        dao.observeAltSpellings(countryId)
            .distinctUntilChanged()
}