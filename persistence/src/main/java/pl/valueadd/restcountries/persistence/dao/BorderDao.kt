package pl.valueadd.restcountries.persistence.dao


import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import pl.valueadd.restcountries.persistence.entity.BorderEntity

@Dao
abstract class BorderDao : BaseDao<BorderEntity>() {

    @Query(value = """
        SELECT *
        FROM borders
        INNER JOIN country_border_join
        ON borders.id = country_border_join.border_id
        WHERE country_border_join.country_id = :countryId
    """)
    abstract fun observeBorders(countryId: String): Flowable<List<BorderEntity>>
}