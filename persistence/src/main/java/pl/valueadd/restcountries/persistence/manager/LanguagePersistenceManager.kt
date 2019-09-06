package pl.valueadd.restcountries.persistence.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import pl.valueadd.restcountries.persistence.dao.LanguageDao
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguagePersistenceManager @Inject constructor(private val dao: LanguageDao) {

    suspend fun saveLanguages(list: List<LanguageEntity>) =
        dao.insert(list)

    suspend fun saveLanguagesIds(list: List<LanguageEntity>): List<Long> =
        dao.insertEntities(list)

    fun observeLanguages(countryId: String): Flow<List<LanguageEntity>> =
        dao.observeLanguages(countryId)
            .distinctUntilChanged()
}