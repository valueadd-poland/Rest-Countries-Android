package pl.valueadd.restcountries.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity

@Dao
abstract class TimeZoneDao : BaseDao<TimeZoneEntity>() {

    @Query(value = """
        SELECT *
        FROM timezones
        INNER JOIN country_time_zone_join
        ON timezones.id = country_time_zone_join.time_zone_id
        WHERE country_time_zone_join.country_id = :countryId
    """)
    abstract fun observeTimeZones(countryId: String): Flowable<List<TimeZoneEntity>>
}