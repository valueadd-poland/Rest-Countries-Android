package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.join.CountryRegionalBlocJoin

@Dao
abstract class CountryRegionalBlocDao : BaseDao<CountryRegionalBlocJoin>()