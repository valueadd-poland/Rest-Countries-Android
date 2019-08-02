package pl.valueadd.restcountries.persistence

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import pl.valueadd.restcountries.persistence.database.RestCountriesDatabase
import toothpick.config.Module

class PersistenceModule(context: Application) : Module() {

    companion object Configuration {
        internal const val DATABASE_NAME = "restcountries.db"
        internal const val CACHE_NAME = "com.valueadd.restcountries.DEVICE_PREFERENCES"
    }

    init {
        // Retrieve database instance and bind into scope.
        val database = provideDatabase(context)

        bind(RestCountriesDatabase::class.java).toInstance(database)

        bind(SharedPreferences::class.java).toInstance(provideSharedPreferences(context))

        bindDao(database)
    }

    private fun bindDao(database: RestCountriesDatabase) {
        bind(ExampleDao::class.java).toInstance(database.exampleDao())
    }

    private fun provideDatabase(context: Application): RestCountriesDatabase =
        Room.databaseBuilder(context, RestCountriesDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    private fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            CACHE_NAME,
            Context.MODE_PRIVATE
        )
}