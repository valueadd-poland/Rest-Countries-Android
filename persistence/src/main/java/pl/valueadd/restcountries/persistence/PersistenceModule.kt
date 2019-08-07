package pl.valueadd.restcountries.persistence

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import pl.valueadd.restcountries.persistence.dao.AltSpellingDao
import pl.valueadd.restcountries.persistence.dao.BorderDao
import pl.valueadd.restcountries.persistence.dao.CallingCodeDao
import pl.valueadd.restcountries.persistence.dao.CountryAltSpellingDao
import pl.valueadd.restcountries.persistence.dao.CountryCallingCodeDao
import pl.valueadd.restcountries.persistence.dao.CountryCurrencyDao
import pl.valueadd.restcountries.persistence.dao.CountryDao
import pl.valueadd.restcountries.persistence.dao.CountryLanguageDao
import pl.valueadd.restcountries.persistence.dao.CountryRegionalBlocDao
import pl.valueadd.restcountries.persistence.dao.CountryTimeZoneDao
import pl.valueadd.restcountries.persistence.dao.CountryTopLevelDomainDao
import pl.valueadd.restcountries.persistence.dao.CurrencyDao
import pl.valueadd.restcountries.persistence.dao.LanguageDao
import pl.valueadd.restcountries.persistence.dao.RegionalBlocDao
import pl.valueadd.restcountries.persistence.dao.TimeZoneDao
import pl.valueadd.restcountries.persistence.dao.TopLevelDomainDao
import pl.valueadd.restcountries.persistence.database.RestCountriesDatabase
import toothpick.config.Module

class PersistenceModule(context: Application) : Module() {

    companion object Configuration {
        internal const val DATABASE_NAME = "restcountries.db"
        internal const val CACHE_NAME = "pl.valueadd.restcountries.DEVICE_PREFERENCES"
    }

    init {
        // Retrieve database instance and bind into scope.
        val database = provideDatabase(context)

        bind(RestCountriesDatabase::class.java).toInstance(database)

        bind(SharedPreferences::class.java).toInstance(provideSharedPreferences(context))

        bindDao(database)
    }

    private fun bindDao(database: RestCountriesDatabase) {
        bind(CountryDao::class.java).toInstance(database.countryDao())
        bind(AltSpellingDao::class.java).toInstance(database.altSpellingDao())
        bind(BorderDao::class.java).toInstance(database.borderDao())
        bind(CallingCodeDao::class.java).toInstance(database.callingCodeDao())
        bind(CountryCallingCodeDao::class.java).toInstance(database.countryCallingCodeDao())
        bind(CountryCurrencyDao::class.java).toInstance(database.countryCurrencyDao())
        bind(CountryLanguageDao::class.java).toInstance(database.countryLanguageDao())
        bind(CountryTopLevelDomainDao::class.java).toInstance(database.countryTopLevelDomainDao())
        bind(CurrencyDao::class.java).toInstance(database.currencyDao())
        bind(LanguageDao::class.java).toInstance(database.languageDao())
        bind(RegionalBlocDao::class.java).toInstance(database.regionalBlocDao())
        bind(TimeZoneDao::class.java).toInstance(database.timeZoneDao())
        bind(TopLevelDomainDao::class.java).toInstance(database.topLevelDomainDao())
        bind(CountryAltSpellingDao::class.java).toInstance(database.countryAltSpellingDao())
        bind(CountryRegionalBlocDao::class.java).toInstance(database.countryRegionalBlocDao())
        bind(CountryTimeZoneDao::class.java).toInstance(database.countryTimeZoneDao())
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