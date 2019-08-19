package pl.valueadd.restcountries.persistence.dao

import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.join.CountryTimeZoneJoin

@Dao
abstract class CountryTimeZoneDao : BaseDao<CountryTimeZoneJoin>()