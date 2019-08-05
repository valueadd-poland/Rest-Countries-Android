package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity

@Dao
abstract class TopLevelDomainDao : BaseDao<TopLevelDomainEntity>()