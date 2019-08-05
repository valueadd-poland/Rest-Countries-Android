package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.LanguageEntity

@Dao
abstract class LanguageDao : BaseDao<LanguageEntity>()