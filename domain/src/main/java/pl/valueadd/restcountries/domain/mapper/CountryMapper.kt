package pl.valueadd.restcountries.domain.mapper

import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.NullValueMappingStrategy
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.domain.model.currency.CurrencyModel
import pl.valueadd.restcountries.domain.model.language.LanguageModel
import pl.valueadd.restcountries.domain.model.language.TranslationsModel
import pl.valueadd.restcountries.domain.model.region.RegionalBlocModel
import pl.valueadd.restcountries.network.dto.country.CountryDto
import pl.valueadd.restcountries.network.dto.currency.CurrencyDto
import pl.valueadd.restcountries.network.dto.language.LanguageDto
import pl.valueadd.restcountries.network.dto.language.TranslationsDto
import pl.valueadd.restcountries.network.dto.region.RegionalBlocDto
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity
import pl.valueadd.restcountries.persistence.entity.BorderEntity
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity
import pl.valueadd.restcountries.persistence.entity.TranslationsEntity

@Mapper(
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
abstract class CountryMapper {

    /* Dto to Entity */

    abstract fun mapCountryDtoToEntity(model: CountryDto): CountryEntity

    abstract fun mapCurrencyDtoToEntity(model: CurrencyDto): CurrencyEntity

    abstract fun mapLanguageDtoToEntity(model: LanguageDto): LanguageEntity

    abstract fun mapTranslationsDtoToEntity(model: TranslationsDto): TranslationsEntity

    abstract fun mapRegionalBlocDtoToEntity(model: RegionalBlocDto): RegionalBlocEntity

    /* Dto List to Entity List */

    abstract fun mapCountryDtosToEntities(list: List<CountryDto>): List<CountryEntity>

    abstract fun mapCurrencyDtosToEntities(list: List<CurrencyDto>): List<CurrencyEntity>

    abstract fun mapLanguageDtosToEntities(list: List<LanguageDto>): List<LanguageEntity>

    abstract fun mapRegionalBlocDtosToEntities(list: List<RegionalBlocEntity>): List<RegionalBlocEntity>

    /* Entity to Model */

    abstract fun mapCountryEntityToModel(model: CountryEntity): CountryModel

    abstract fun mapCurrencyEntityToModel(model: CurrencyEntity): CurrencyModel

    abstract fun mapLanguageEntityToModel(model: LanguageEntity): LanguageModel

    abstract fun mapTranslationsEntityToModel(model: TranslationsEntity): TranslationsModel

    abstract fun mapRegionalBlocEntityToModel(model: RegionalBlocEntity): RegionalBlocModel

    /* Entity List to Model List */

    abstract fun mapCountryEntitiesToModels(list: List<CountryEntity>): List<CountryModel>

    abstract fun mapCurrencyEntitiesToModels(list: List<CurrencyEntity>): List<CurrencyModel>

    abstract fun mapLanguageEntitiesToModels(list: List<CurrencyEntity>): List<CurrencyModel>

    abstract fun mapRegionalBlocEntitiesToModels(list: List<RegionalBlocEntity>): List<RegionalBlocModel>

    /* Custom */

    fun mapTopLevelDomainDtoToEntity(value: String): TopLevelDomainEntity =
        TopLevelDomainEntity(name = value)

    fun mapTopLevelDomainDtosToEntities(values: List<String>): List<TopLevelDomainEntity> =
        values.map(::mapTopLevelDomainDtoToEntity)

    fun mapCallingCodeDtoToEntity(value: String): CallingCodeEntity =
        CallingCodeEntity(name = value)

    fun mapCallingCodeDtosToEntities(values: List<String>): List<CallingCodeEntity> =
        values.map(::mapCallingCodeDtoToEntity)

    fun mapAltSpellingDtoToEntity(value: String): AltSpellingEntity =
        AltSpellingEntity(name = value)

    fun mapAltSpellingDtosToEntities(values: List<String>): List<AltSpellingEntity> =
        values.map(::mapAltSpellingDtoToEntity)

    fun mapTimeZoneDtoToEntity(value: String): TimeZoneEntity =
        TimeZoneEntity(name = value)

    fun mapTimeZoneDtosToEntities(values: List<String>): List<TimeZoneEntity> =
        values.map(::mapTimeZoneDtoToEntity)

    fun mapBorderDtoToEntity(value: String): BorderEntity =
        BorderEntity(name = value)

    fun mapBorderDtosToEntities(values: List<String>): List<BorderEntity> =
        values.map(::mapBorderDtoToEntity)

}