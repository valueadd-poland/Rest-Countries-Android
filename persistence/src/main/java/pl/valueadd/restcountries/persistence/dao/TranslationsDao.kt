package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.TranslationsEntity

@Dao
abstract class TranslationsDao : BaseDao<TranslationsEntity>()