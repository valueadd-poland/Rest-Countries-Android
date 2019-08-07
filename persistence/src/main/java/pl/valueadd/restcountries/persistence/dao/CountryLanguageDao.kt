package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.join.CountryLanguageJoin

@Dao
abstract class CountryLanguageDao : BaseDao<CountryLanguageJoin>()