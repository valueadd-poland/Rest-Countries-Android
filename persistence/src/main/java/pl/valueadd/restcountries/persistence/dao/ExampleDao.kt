package pl.valueadd.restcountries.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import pl.valueadd.restcountries.persistence.entity.ExampleEntity
import io.reactivex.Flowable

@Dao
abstract class ExampleDao : BaseDao<ExampleEntity>() {

    @Query("""
        SELECT *
        FROM examples
        LIMIT 1
    """)
    abstract fun observeExample(): Flowable<ExampleEntity>

    @Query("""
        SELECT *
        FROM examples
    """)
    abstract fun observeAllExamples(): Flowable<List<ExampleEntity>>
}