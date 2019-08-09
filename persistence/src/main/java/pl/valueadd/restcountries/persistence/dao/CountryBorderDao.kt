package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.join.CountryBorderJoin

@Dao
abstract class CountryBorderDao : BaseDao<CountryBorderJoin>()