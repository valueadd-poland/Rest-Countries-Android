package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity

@Dao
abstract class AltSpellingDao : BaseDao<AltSpellingEntity>()