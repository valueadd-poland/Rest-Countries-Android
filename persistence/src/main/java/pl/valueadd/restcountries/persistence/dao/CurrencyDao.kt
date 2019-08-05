package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity

@Dao
abstract class CurrencyDao : BaseDao<CurrencyEntity>()