package pl.valueadd.restcountries.persistence.model

import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity
import pl.valueadd.restcountries.persistence.entity.BorderEntity
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity

data class CountryMerge(

    var country: CountryEntity = CountryEntity(),

    var tepLevelDomain: List<TopLevelDomainEntity> = emptyList(),

    var callingCodes: List<CallingCodeEntity> = emptyList(),

    var altSpellings: List<AltSpellingEntity> = emptyList(),

    var timezones: List<TimeZoneEntity> = emptyList(),

    var borders: List<BorderEntity> = emptyList(),

    var currencies: List<CurrencyEntity> = emptyList(),

    var languages: List<LanguageEntity> = emptyList(),

    var regionalBlocs: List<RegionalBlocEntity> = emptyList()

)