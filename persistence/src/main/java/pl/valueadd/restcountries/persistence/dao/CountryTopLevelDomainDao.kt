package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.CountryTopLevelDomainJoin

@Dao
abstract class CountryTopLevelDomainDao : BaseDao<CountryTopLevelDomainJoin>()