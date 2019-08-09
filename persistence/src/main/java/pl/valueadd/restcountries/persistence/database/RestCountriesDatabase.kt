package pl.valueadd.restcountries.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.valueadd.restcountries.persistence.dao.AltSpellingDao
import pl.valueadd.restcountries.persistence.dao.BorderDao
import pl.valueadd.restcountries.persistence.dao.CallingCodeDao
import pl.valueadd.restcountries.persistence.dao.CountryAltSpellingDao
import pl.valueadd.restcountries.persistence.dao.CountryBorderDao
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
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity
import pl.valueadd.restcountries.persistence.entity.BorderEntity
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryAltSpellingJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryBorderJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryCallingCodeJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryCurrencyJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryLanguageJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryRegionalBlocJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryTimeZoneJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryTopLevelDomainJoin

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        CallingCodeEntity::class,
        CountryCallingCodeJoin::class,
        CountryCurrencyJoin::class,
        CountryEntity::class,
        CountryLanguageJoin::class,
        CountryTopLevelDomainJoin::class,
        CurrencyEntity::class,
        LanguageEntity::class,
        TopLevelDomainEntity::class,
        RegionalBlocEntity::class,
        AltSpellingEntity::class,
        BorderEntity::class,
        TimeZoneEntity::class,
        CountryAltSpellingJoin::class,
        CountryRegionalBlocJoin::class,
        CountryTimeZoneJoin::class,
        CountryBorderJoin::class
    ]
)
abstract class RestCountriesDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    abstract fun altSpellingDao(): AltSpellingDao

    abstract fun borderDao(): BorderDao

    abstract fun callingCodeDao(): CallingCodeDao

    abstract fun countryCallingCodeDao(): CountryCallingCodeDao

    abstract fun countryCurrencyDao(): CountryCurrencyDao

    abstract fun countryLanguageDao(): CountryLanguageDao

    abstract fun countryTopLevelDomainDao(): CountryTopLevelDomainDao

    abstract fun currencyDao(): CurrencyDao

    abstract fun languageDao(): LanguageDao

    abstract fun regionalBlocDao(): RegionalBlocDao

    abstract fun timeZoneDao(): TimeZoneDao

    abstract fun topLevelDomainDao(): TopLevelDomainDao

    abstract fun countryAltSpellingDao(): CountryAltSpellingDao

    abstract fun countryRegionalBlocDao(): CountryRegionalBlocDao

    abstract fun countryTimeZoneDao(): CountryTimeZoneDao

    abstract fun countryBorderDao(): CountryBorderDao

}