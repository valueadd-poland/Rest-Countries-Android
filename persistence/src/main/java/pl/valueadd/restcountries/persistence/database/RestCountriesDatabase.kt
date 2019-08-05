package pl.valueadd.restcountries.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.valueadd.restcountries.persistence.dao.AltSpellingDao
import pl.valueadd.restcountries.persistence.dao.BorderDao
import pl.valueadd.restcountries.persistence.dao.CallingCodeDao
import pl.valueadd.restcountries.persistence.dao.CountryCallingCodeDao
import pl.valueadd.restcountries.persistence.dao.CountryCurrencyDao
import pl.valueadd.restcountries.persistence.dao.CountryDao
import pl.valueadd.restcountries.persistence.dao.CountryLanguageDao
import pl.valueadd.restcountries.persistence.dao.CountryTopLevelDomainDao
import pl.valueadd.restcountries.persistence.dao.CurrencyDao
import pl.valueadd.restcountries.persistence.dao.LanguageDao
import pl.valueadd.restcountries.persistence.dao.LatLngDao
import pl.valueadd.restcountries.persistence.dao.RegionalBlocDao
import pl.valueadd.restcountries.persistence.dao.TimeZoneDao
import pl.valueadd.restcountries.persistence.dao.TopLevelDomainDao
import pl.valueadd.restcountries.persistence.dao.TranslationsDao
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity
import pl.valueadd.restcountries.persistence.entity.BorderEntity
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity
import pl.valueadd.restcountries.persistence.entity.CountryCallingCodeJoin
import pl.valueadd.restcountries.persistence.entity.CountryCurrencyJoin
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.CountryLanguageJoin
import pl.valueadd.restcountries.persistence.entity.CountryTopLevelDomainJoin
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity
import pl.valueadd.restcountries.persistence.entity.TranslationsEntity

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
        TimeZoneEntity::class
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

    abstract fun latLngDao(): LatLngDao

    abstract fun regionalBlocDao(): RegionalBlocDao

    abstract fun timeZoneDao(): TimeZoneDao

    abstract fun topLevelDomainDao(): TopLevelDomainDao

    abstract fun translationsDao(): TranslationsDao

}