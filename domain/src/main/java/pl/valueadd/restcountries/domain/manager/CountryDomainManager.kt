package pl.valueadd.restcountries.domain.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Function9
import pl.valueadd.restcountries.domain.mapper.CountryMapper
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.domain.model.currency.CurrencyModel
import pl.valueadd.restcountries.domain.model.language.LanguageModel
import pl.valueadd.restcountries.domain.model.region.RegionalBlocModel
import pl.valueadd.restcountries.network.dto.country.CountryDto
import pl.valueadd.restcountries.network.manager.CountryNetworkManager
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
import pl.valueadd.restcountries.persistence.manager.AltSpellingPersistenceManager
import pl.valueadd.restcountries.persistence.manager.BorderPersistenceManager
import pl.valueadd.restcountries.persistence.manager.CallingCodePersistenceManager
import pl.valueadd.restcountries.persistence.manager.CountryPersistenceManager
import pl.valueadd.restcountries.persistence.manager.CurrencyPersistenceManager
import pl.valueadd.restcountries.persistence.manager.LanguagePersistenceManager
import pl.valueadd.restcountries.persistence.manager.RegionalBlocPersistenceManager
import pl.valueadd.restcountries.persistence.manager.TimeZonePersistenceManager
import pl.valueadd.restcountries.persistence.manager.TopLevelDomainPersistenceManager
import pl.valueadd.restcountries.utility.reactivex.immediateSingle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryDomainManager @Inject constructor(
    private val network: CountryNetworkManager,
    private val persistence: CountryPersistenceManager,
    private val callingCodePersistence: CallingCodePersistenceManager,
    private val altSpellingPersistence: AltSpellingPersistenceManager,
    private val topLevelDomainPersistence: TopLevelDomainPersistenceManager,
    private val currencyPersistence: CurrencyPersistenceManager,
    private val languagePersistence: LanguagePersistenceManager,
    private val regionalBlocPersistence: RegionalBlocPersistenceManager,
    private val timeZonePersistence: TimeZonePersistenceManager,
    private val borderPersistence: BorderPersistenceManager,
    private val mapper: CountryMapper
) {

    fun observeCountry(countryId: String): Flowable<CountryModel> =
        Flowable.combineLatest(
            observeCountryById(countryId),
            observeAltSpellings(countryId),
            observeBorders(countryId),
            observeCallingCodes(countryId),
            observeCurrencies(countryId),
            observeLanguages(countryId),
            observeRegionalBlocs(countryId),
            observeTimeZones(countryId),
            observeTopLevelDomains(countryId),
            Function9 { country,
                        altSpellings,
                        borders,
                        callingCodes,
                        currencies,
                        languages,
                        regionalBlocs,
                        timezones,
                        topLevelDomains ->

                country.also {
                    it.altSpellings = altSpellings
                    it.borders = borders
                    it.callingCodes = callingCodes
                    it.currencies = currencies
                    it.languages = languages
                    it.regionalBlocs = regionalBlocs
                    it.timezones = timezones
                    it.topLevelDomains = topLevelDomains
                }
            }
        )

    fun observeCountries(countyIds: List<String>): Flowable<List<CountryModel>> {
        return persistence.observeCountries(countyIds)
            .map(mapper::mapCountryEntitiesToModels)
    }

    fun observeAllCountries(): Flowable<List<CountryModel>> =
        persistence
            .observeAllCountries()
            .map(mapper::mapCountryEntitiesToModels)

    fun downloadAllCountries(): Completable =
        network
            .getAllCountries()
            .flatMapCompletable(::saveAllCountries)

    /* Save all Countries */

    private fun saveAllCountries(list: List<CountryDto>): Completable {
        val saveEntities: MutableList<Completable> = mutableListOf()
        val saveRelations: MutableList<Completable> = mutableListOf()

        return immediateSingle {
            mutableListOf<CountryEntity>().also { entities ->
                for (dto in list) {

                    val entity: CountryEntity = mapper.mapCountryDtoToEntity(dto)

                    saveEntities.add(saveCallingCodesFor(entity.id, mapper.mapCallingCodeDtosToEntities(dto.callingCodes)))
                    saveEntities.add(saveTopLevelDomainsFor(entity.id, mapper.mapTopLevelDomainDtosToEntities(dto.topLevelDomains)))
                    saveEntities.add(saveAltSpellingsFor(entity.id, mapper.mapAltSpellingDtosToEntities(dto.altSpellings)))
                    saveEntities.add(saveTimeZonesFor(entity.id, mapper.mapTimeZoneDtosToEntities(dto.timezones)))

                    mapper.mapRegionalBlocDtosToEntities(dto.regionalBlocs).let {
                        saveEntities.add(regionalBlocPersistence.saveRegionalBlocs(it))
                        saveRelations.add(saveRegionalBlocsFor(entity.id, it))
                    }

                    mapper.mapLanguageDtosToEntities(dto.languages).let {
                        saveEntities.add(languagePersistence.saveLanguages(it))
                        saveRelations.add(saveLanguageJoinsFor(entity.id, it))
                    }

                    mapper.mapCurrencyDtosToEntities(dto.currencies).let {
                        saveEntities.add(currencyPersistence.saveCurrencies(it))
                        saveRelations.add(saveCurrencyJoinsFor(entity.id, it))

                    }

                    mapper.mapBorderDtosToEntities(dto.borders).let {
                        saveEntities.add(borderPersistence.saveBorders(it))
                        saveRelations.add(saveBorderJoinsFor(entity.id, it))
                    }

                    entities.add(entity)
                }
            }
        }.flatMapCompletable(persistence::saveCountries)
            .andThen(Completable.merge(saveEntities))
            .andThen(Completable.merge(saveRelations))
    }

    private fun saveCurrencyJoinsFor(countryId: String, entities: List<CurrencyEntity>): Completable {
        val map: Single<List<CountryCurrencyJoin>> = immediateSingle {
            entities.map { CountryCurrencyJoin(countryId, it.id) }
        }

        return map.flatMapCompletable(persistence::saveCountryCurrencyJoins)
    }

    private fun saveLanguageJoinsFor(countryId: String, entities: List<LanguageEntity>): Completable {
        val map: Single<List<CountryLanguageJoin>> = immediateSingle {
            entities.map { CountryLanguageJoin(countryId, it.id) }
        }

        return map.flatMapCompletable(persistence::saveCountryLanguageJoins)
    }

    private fun saveBorderJoinsFor(countryId: String, entities: List<BorderEntity>): Completable {
        val map: Single<List<CountryBorderJoin>> = immediateSingle {
            entities.map { CountryBorderJoin(countryId, it.id) }
        }

        return map.flatMapCompletable(persistence::saveCountryBorderJoins)
    }

    private fun saveRegionalBlocsFor(countryId: String, entities: List<RegionalBlocEntity>): Completable {
        val map: Single<List<CountryRegionalBlocJoin>> = immediateSingle {
            entities.map { CountryRegionalBlocJoin(countryId, it.id) }
        }

        return map.flatMapCompletable(persistence::saveCountryRegionalBlocJoins)
    }

    private fun saveCallingCodesFor(countryId: String, entities: List<CallingCodeEntity>): Completable =
        callingCodePersistence.saveCallingCodesIds(entities)
            .map { list ->
                list.map { CountryCallingCodeJoin(countryId, it) }
            }.flatMapCompletable(persistence::saveCountryCallingCodeJoins)

    private fun saveTopLevelDomainsFor(countryId: String, entities: List<TopLevelDomainEntity>): Completable =
        topLevelDomainPersistence.saveTopLevelDomainsIds(entities)
            .map { list ->
                list.map { CountryTopLevelDomainJoin(countryId, it) }
            }.flatMapCompletable(persistence::saveCountryTopLevelDomainJoins)

    private fun saveAltSpellingsFor(countryId: String, entities: List<AltSpellingEntity>): Completable =
        altSpellingPersistence.saveAltSpellingsIds(entities)
            .map { list ->
                list.map { CountryAltSpellingJoin(countryId, it) }
            }.flatMapCompletable(persistence::saveCountryAltSpellingJoins)

    private fun saveTimeZonesFor(countryId: String, entities: List<TimeZoneEntity>): Completable =
        timeZonePersistence.saveTimezonesIds(entities)
            .map { list ->
                list.map { CountryTimeZoneJoin(countryId, it) }
            }.flatMapCompletable(persistence::saveCountryTimeZoneJoins)

    /* Fetch Country */

    private fun observeCountryById(countryId: String): Flowable<CountryModel> =
        persistence
            .observeCountry(countryId)
            .map(mapper::mapCountryEntityToModel)

    private fun observeAltSpellings(countryId: String): Flowable<List<String>> =
        altSpellingPersistence
            .observeAltSpellings(countryId)
            .map(mapper::mapAltSpellingEntitiesToModels)

    private fun observeBorders(countryId: String): Flowable<List<String>> =
        borderPersistence
            .observeBorders(countryId)
            .map(mapper::mapBorderEntitiesToModels)

    private fun observeCallingCodes(countryId: String): Flowable<List<String>> =
        callingCodePersistence
            .observeCallingCodes(countryId)
            .map(mapper::mapCallingCodeEntitiesToModels)

    private fun observeCurrencies(countryId: String): Flowable<List<CurrencyModel>> =
        currencyPersistence
            .observeCurriencies(countryId)
            .map(mapper::mapCurrencyEntitiesToModels)

    private fun observeLanguages(countryId: String): Flowable<List<LanguageModel>> =
        languagePersistence
            .observeLanguages(countryId)
            .map(mapper::mapLanguageEntitiesToModels)

    private fun observeRegionalBlocs(countryId: String): Flowable<List<RegionalBlocModel>> =
        regionalBlocPersistence
            .observeRegionalBlocs(countryId)
            .map(mapper::mapRegionalBlocEntitiesToModels)

    private fun observeTimeZones(countryId: String): Flowable<List<String>> =
        timeZonePersistence
            .observeTimeZones(countryId)
            .map(mapper::mapTimeZoneEntitiesToModels)

    private fun observeTopLevelDomains(countryId: String): Flowable<List<String>> =
        topLevelDomainPersistence
            .observeTopLevelDomains(countryId)
            .map(mapper::mapTopLevelDomainEntitiesToModels)
}