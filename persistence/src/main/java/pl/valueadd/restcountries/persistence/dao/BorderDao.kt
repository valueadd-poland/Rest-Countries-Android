package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.BorderEntity

@Dao
abstract class BorderDao : BaseDao<BorderEntity>()