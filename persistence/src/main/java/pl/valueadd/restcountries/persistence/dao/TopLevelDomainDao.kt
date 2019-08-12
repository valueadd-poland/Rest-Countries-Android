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
        INNER JOIN country_top_level_domain_join
        ON top_level_domains.id = country_top_level_domain_join.top_level_domain_id
        WHERE country_top_level_domain_join.country_id = :countryId
    """)
    abstract fun observeTopLevelDomains(countryId: String): Flowable<List<TopLevelDomainEntity>>
}