package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity

@Dao
abstract class TimeZoneDao : BaseDao<TimeZoneEntity>()