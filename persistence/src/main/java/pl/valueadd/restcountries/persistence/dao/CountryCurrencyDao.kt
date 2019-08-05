package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.CountryCurrencyJoin

@Dao
abstract class CountryCurrencyDao : BaseDao<CountryCurrencyJoin>()