package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity

@Dao
abstract class CallingCodeDao : BaseDao<CallingCodeEntity>()