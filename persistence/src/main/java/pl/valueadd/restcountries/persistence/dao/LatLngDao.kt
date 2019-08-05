package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.LatLngEntity

@Dao
abstract class LatLngDao : BaseDao<LatLngEntity>()