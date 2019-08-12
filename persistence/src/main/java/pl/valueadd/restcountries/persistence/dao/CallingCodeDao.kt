package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity

@Dao
abstract class CallingCodeDao : BaseDao<CallingCodeEntity>() {

    @Query(value = """
        SELECT *
        FROM calling_codes
        INNER JOIN country_calling_code_join
        ON calling_codes.id = country_calling_code_join.calling_code_id
        WHERE country_calling_code_join.country_id = :countryId
    """)
    abstract fun observeCallingCodes(countryId: String): Flowable<List<CallingCodeEntity>>
}