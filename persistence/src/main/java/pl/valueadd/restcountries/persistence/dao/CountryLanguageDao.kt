package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.CountryLanguageJoin

@Dao
abstract class CountryLanguageDao : BaseDao<CountryLanguageJoin>()