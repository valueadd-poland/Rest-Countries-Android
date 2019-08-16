package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity

@Dao
abstract class TopLevelDomainDao : BaseDao<TopLevelDomainEntity>() {

    @Query(value = """
        SELECT *
        FROM top_level_domains
        WHERE top_level_domains.country_id = :countryId
    """)
    abstract fun observeTopLevelDomains(countryId: String): Flowable<List<TopLevelDomainEntity>>
}