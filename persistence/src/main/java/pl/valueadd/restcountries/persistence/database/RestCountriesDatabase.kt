package pl.valueadd.restcountries.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.valueadd.restcountries.persistence.dao.AltSpellingDao
import pl.valueadd.restcountries.persistence.dao.CallingCodeDao
import pl.valueadd.restcountries.persistence.dao.CountryBorderDao
import pl.valueadd.restcountries.persistence.dao.CountryCurrencyDao
import pl.valueadd.restcountries.persistence.dao.CountryDao
import pl.valueadd.restcountries.persistence.dao.CountryLanguageDao
import pl.valueadd.restcountries.persistence.dao.CountryRegionalBlocDao
import pl.valueadd.restcountries.persistence.dao.CountryTimeZoneDao
import pl.valueadd.restcountries.persistence.dao.CurrencyDao
import pl.valueadd.restcountries.persistence.dao.LanguageDao
import pl.valueadd.restcountries.persistence.dao.RegionalBlocDao
import pl.valueadd.restcountries.persistence.dao.TimeZoneDao
import pl.valueadd.restcountries.persistence.dao.TopLevelDomainDao
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity
import pl.valueadd.restcountries.persistence.model.Border
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryBorderJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryCurrencyJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryLanguageJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryRegionalBlocJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryTimeZoneJoin

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        CallingCodeEntity::class,
        CountryCurrencyJoin::class,
        CountryEntity::class,
        CountryLanguageJoin::class,
        CurrencyEntity::class,
        LanguageEntity::class,
        TopLevelDomainEntity::class,
        RegionalBlocEntity::class,
        AltSpellingEntity::class,
        TimeZoneEntity::class,
        CountryRegionalBlocJoin::class,
        CountryTimeZoneJoin::class,
        CountryBorderJoin::class
    ]
)
abstract class RestCountriesDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    abstract fun altSpellingDao(): AltSpellingDao

    abstract fun callingCodeDao(): CallingCodeDao

    abstract fun countryCurrencyDao(): CountryCurrencyDao

    abstract fun countryLanguageDao(): CountryLanguageDao

    abstract fun currencyDao(): CurrencyDao

    abstract fun languageDao(): LanguageDao

    abstract fun regionalBlocDao(): RegionalBlocDao

    abstract fun timeZoneDao(): TimeZoneDao

    abstract fun topLevelDomainDao(): TopLevelDomainDao

    abstract fun countryRegionalBlocDao(): CountryRegionalBlocDao

    abstract fun countryTimeZoneDao(): CountryTimeZoneDao

    abstract fun countryBorderDao(): CountryBorderDao

}