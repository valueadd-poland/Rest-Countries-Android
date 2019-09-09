package pl.valueadd.restcountries.domain.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.functions.Function9
import pl.valueadd.restcountries.domain.mapper.CountryMapper
import pl.valueadd.restcountries.domain.model.country.CountryFlatModel
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.domain.model.currency.CurrencyModel
import pl.valueadd.restcountries.domain.model.helper.Filter
import pl.valueadd.restcountries.domain.model.language.LanguageModel
import pl.valueadd.restcountries.domain.model.region.RegionalBlocModel
import pl.valueadd.restcountries.network.dto.country.CountryDto
import pl.valueadd.restcountries.network.manager.CountryNetworkManager
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryBorderJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryCurrencyJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryLanguageJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryRegionalBlocJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryTimeZoneJoin
import pl.valueadd.restcountries.persistence.manager.AltSpellingPersistenceManager
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

    fun observeAllCountries(): Flowable<List<CountryModel>> =
        persistence
            .observeAllCountries()
            .map(mapper::mapCountryEntitiesToModels)

    fun observeCountries(query: String, filter: Filter<CountryModel>): Flowable<List<CountryModel>> =
        persistence
            .observeCountries(query, filter.isAscending)
            .map(mapper::mapCountryEntitiesToModels)
            .map { list ->
                if (filter.hasComparator) {
                    list.sortedWith(filter.comparator)
                } else list
            }

    fun downloadAllCountries(): Completable =
        network
            .getAllCountries()
            .flatMapCompletable(::saveAllCountries)

    /* Save all Countries */

    private fun saveAllCountries(dtoList: List<CountryDto>): Completable {
        val callingCodes = hashSetOf<CallingCodeEntity>()
        val altSpellings = hashSetOf<AltSpellingEntity>()
        val topLevelDomains = hashSetOf<TopLevelDomainEntity>()
        val regionalBlocks = hashSetOf<RegionalBlocEntity>()
        val languages = hashSetOf<LanguageEntity>()
        val currencies = hashSetOf<CurrencyEntity>()

        val timezonesRelations = mutableListOf<CountryTimeZoneJoin>()
        val regionalBlocksRelations = mutableListOf<CountryRegionalBlocJoin>()
        val languagesRelations = mutableListOf<CountryLanguageJoin>()
        val currenciesRelations = mutableListOf<CountryCurrencyJoin>()
        val bordersRelations = mutableListOf<CountryBorderJoin>()

        val saveEntities = mutableListOf<Completable>()
        val saveRelations = mutableListOf<Completable>()

        return immediateSingle {
            mutableListOf<CountryEntity>().also { entities ->
                for (dto in dtoList) {

                    val entity: CountryEntity = mapper.mapCountryDtoToEntity(dto)

                    mapper.mapTimeZoneDtosToEntities(dto.timezones).let {
                        timezonesRelations += timeZonePersistence.saveTimezonesIds(it)
                            .blockingGet()
                            .map { id -> CountryTimeZoneJoin(entity.id, id) }
                    }

                    mapper.mapCallingCodeDtosToEntities(dto.callingCodes, entity.id).let {
                        callingCodes += it
                    }

                    mapper.mapAltSpellingDtosToEntities(dto.altSpellings, entity.id).let {
                        altSpellings += it
                    }

                    mapper.mapTopLevelDomainDtosToEntities(dto.topLevelDomains, entity.id).let {
                        topLevelDomains += it
                    }

                    mapper.mapRegionalBlocDtosToEntities(dto.regionalBlocs).let { list ->
                        regionalBlocks += list
                        regionalBlocksRelations += list.map { CountryRegionalBlocJoin(entity.id, it.id) }
                    }

                    mapper.mapLanguageDtosToEntities(dto.languages).let { list ->
                        languages += list
                        languagesRelations += list.map { CountryLanguageJoin(entity.id, it.id) }
                    }

                    mapper.mapCurrencyDtosToEntities(dto.currencies).let { list ->
                        currencies += list
                        currenciesRelations += list.map { CountryCurrencyJoin(entity.id, it.id) }
                    }

                    mapper.mapBorderDtosToEntities(dto.borders).let { list ->
                        bordersRelations += list.map { CountryBorderJoin(entity.id, it.id) }
                    }

                    entities.add(entity)
                }

                saveEntities += listOf(
                    callingCodePersistence.saveCallingCodes(callingCodes.toList()),
                    altSpellingPersistence.saveAltSpellings(altSpellings.toList()),
                    topLevelDomainPersistence.saveTopLevelDomains(topLevelDomains.toList()),
                    regionalBlocPersistence.saveRegionalBlocs(regionalBlocks.toList()),
                    languagePersistence.saveLanguages(languages.toList()),
                    currencyPersistence.saveCurrencies(currencies.toList())
                )

                saveRelations += listOf(
                    persistence.saveCountryTimeZoneJoins(timezonesRelations.toList()),
                    persistence.saveCountryRegionalBlocJoins(regionalBlocksRelations.toList()),
                    persistence.saveCountryLanguageJoins(languagesRelations.toList()),
                    persistence.saveCountryCurrencyJoins(currenciesRelations.toList()),
                    persistence.saveCountryBorderJoins(bordersRelations.toList())
                )
            }
        }.flatMapCompletable(persistence::saveCountries)
            .andThen(Completable.merge(saveEntities))
            .andThen(Completable.merge(saveRelations))
    }

    /* Fetch Country */

    private fun observeCountryById(countryId: String): Flowable<CountryModel> =
        persistence
            .observeCountry(countryId)
            .map(mapper::mapCountryEntityToModel)

    private fun observeAltSpellings(countryId: String): Flowable<List<String>> =
        altSpellingPersistence
            .observeAltSpellings(countryId)
            .map(mapper::mapAltSpellingEntitiesToModels)

    private fun observeBorders(countryId: String): Flowable<List<CountryFlatModel>> =
        persistence
            .observeBorders(countryId)
            .map(mapper::mapCountriesFlatToModels)

    private fun observeCallingCodes(countryId: String): Flowable<List<String>> =
        callingCodePersistence
            .observeCallingCodes(countryId)
            .map(mapper::mapCallingCodeEntitiesToModels)

    private fun observeCurrencies(countryId: String): Flowable<List<CurrencyModel>> =
        currencyPersistence
            .observeCurrencies(countryId)
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