package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.join.CountryAltSpellingJoin

@Dao
abstract class CountryAltSpellingDao : BaseDao<CountryAltSpellingJoin>()