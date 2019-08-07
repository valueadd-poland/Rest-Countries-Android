package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.join.CountryCallingCodeJoin

@Dao
abstract class CountryCallingCodeDao : BaseDao<CountryCallingCodeJoin>()