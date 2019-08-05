package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.CountryCallingCodeJoin

@Dao
abstract class CountryCallingCodeDao : BaseDao<CountryCallingCodeJoin>()