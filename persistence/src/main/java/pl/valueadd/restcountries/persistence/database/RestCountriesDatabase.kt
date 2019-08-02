package pl.valueadd.restcountries.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        ExampleEntity::class
    ]
)
abstract class RestCountriesDatabase : RoomDatabase() {

    abstract fun exampleDao(): ExampleDao
}