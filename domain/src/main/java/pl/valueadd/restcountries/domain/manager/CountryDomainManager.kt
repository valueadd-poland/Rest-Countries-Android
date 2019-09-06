package pl.valueadd.restcountries.domain.manager

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
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
import pl.valueadd.restcountries.utility.common.map
import pl.valueadd.restcountries.utility.coroutines.combineTransform
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.system.measureTimeMillis

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

    fun observeCountry(countryId: String): Flow<CountryModel> = combineTransform(
        observeCountryById(countryId),
        observeAltSpellings(countryId),
        observeBorders(countryId),
        observeCallingCodes(countryId),
        observeCurrencies(countryId),
        observeLanguages(countryId),
        observeRegionalBlocs(countryId),
        observeTimeZones(countryId),
        observeTopLevelDomains(countryId)
    ) { country,
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
        emit(country)
    }.conflate()
        .flowOn(Dispatchers.IO)

    fun observeAllCountries(): Flow<List<CountryModel>> =
        persistence
            .observeAllCountries()
            .map(mapper::mapCountryEntitiesToModels)

    fun observeCountries(query: String, filter: Filter<CountryModel>): Flow<List<CountryModel>> =
        persistence
            .observeCountries(query, filter.isAscending)
            .map(mapper::mapCountryEntitiesToModels)
            .map { list ->
                if (filter.hasComparator) {
                    list.sortedWith(filter.comparator)
                } else list
            }

    suspend fun downloadAllCountries() {
        val countries = network.getAllCountries()
        val savingTime = measureTimeMillis {
            saveAllCountries(countries)
        }
        Timber.d("savingTime: " + savingTime)
    }

    /* Save all Countries */

    private suspend fun saveAllCountries(list: List<CountryDto>) {
        val timezonesRelations = mutableListOf<CountryTimeZoneJoin>()
        val callingCodes = hashSetOf<CallingCodeEntity>()
        val altSpellings = hashSetOf<AltSpellingEntity>()
        val topLevelDomains = hashSetOf<TopLevelDomainEntity>()
        val regionalBlocks = hashSetOf<RegionalBlocEntity>()
        val regionalBlocksRelations = mutableListOf<CountryRegionalBlocJoin>()
        val languages = hashSetOf<LanguageEntity>()
        val languagesRelations = mutableListOf<CountryLanguageJoin>()
        val currencies = hashSetOf<CurrencyEntity>()
        val currenciesRelations = mutableListOf<CountryCurrencyJoin>()
        val bordersRelations = mutableListOf<CountryBorderJoin>()

        var entities = emptyList<CountryEntity>()
        val mappingTime = measureTimeMillis {
            entities = list.map { dto ->
                val entity: CountryEntity = mapper.mapCountryDtoToEntity(dto)

                val timeZoneEntities = mapper.mapTimeZoneDtosToEntities(dto.timezones) // TODO saving timezones improvement
                timezonesRelations += timeZonePersistence.saveTimezonesIds(timeZoneEntities)
                    .map { id ->
                        CountryTimeZoneJoin(entity.id, id)
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

                mapper.mapRegionalBlocDtosToEntities(dto.regionalBlocs).let {
                    regionalBlocks += it
                    regionalBlocksRelations += it.map { CountryRegionalBlocJoin(entity.id, it.id) }
                }

                mapper.mapLanguageDtosToEntities(dto.languages).let {
                    languages += it
                    languagesRelations += it.map { CountryLanguageJoin(entity.id, it.id) }
                }

                mapper.mapCurrencyDtosToEntities(dto.currencies).let {
                    currencies += it
                    currenciesRelations += it.map { CountryCurrencyJoin(entity.id, it.id) }
                }

                mapper.mapBorderDtosToEntities(dto.borders).let {
                    bordersRelations += it.map { CountryBorderJoin(entity.id, it.id) }
                }

                return@map entity
            }
        }
        Timber.d("mappingTime: " + mappingTime) // TODO remove time measuring and split this big method to small
        val timePersistence = measureTimeMillis {
            persistence.saveCountries(entities)
        }
        Timber.d("timePersistence: " + timePersistence)
        val timeEntitiesJobs = measureTimeMillis {
            saveTimeZonesFor(timezonesRelations)
            callingCodePersistence.saveCallingCodes(callingCodes.toList())
            altSpellingPersistence.saveAltSpellings(altSpellings.toList())
            topLevelDomainPersistence.saveTopLevelDomains(topLevelDomains.toList())
            regionalBlocPersistence.saveRegionalBlocs(regionalBlocks.toList())
            languagePersistence.saveLanguages(languages.toList())
            currencyPersistence.saveCurrencies(currencies.toList())
        }
        Timber.d("timeEntitiesJobs: " + timeEntitiesJobs)
        val timeRelationsJobs = measureTimeMillis {
            saveRegionalBlocsFor(regionalBlocksRelations)
            saveLanguageJoinsFor(languagesRelations)
            saveCurrencyJoinsFor(currenciesRelations)
            saveBorderJoinsFor(bordersRelations)
        }
        Timber.d("timeRelationsJobs: " + timeRelationsJobs)
    }

    private suspend fun saveCurrencyJoinsFor(joins: List<CountryCurrencyJoin>) {
        persistence.saveCountryCurrencyJoins(joins)
    }

    private suspend fun saveLanguageJoinsFor(joins: List<CountryLanguageJoin>) {
        persistence.saveCountryLanguageJoins(joins)
    }

    private suspend fun saveBorderJoinsFor(joins: List<CountryBorderJoin>) {
        persistence.saveCountryBorderJoins(joins)
    }

    private suspend fun saveRegionalBlocsFor(joins: List<CountryRegionalBlocJoin>) {
        persistence.saveCountryRegionalBlocJoins(joins)
    }

    private suspend fun saveTimeZonesFor(joins: List<CountryTimeZoneJoin>) {
        persistence.saveCountryTimeZoneJoins(joins)
    }

    /* Fetch Country */

    private fun observeCountryById(countryId: String): Flow<CountryModel> =
        persistence
            .observeCountry(countryId)
            .map(mapper::mapCountryEntityToModel)

    private fun observeAltSpellings(countryId: String): Flow<List<String>> =
        altSpellingPersistence
            .observeAltSpellings(countryId)
            .map(mapper::mapAltSpellingEntitiesToModels)

    private fun observeBorders(countryId: String): Flow<List<CountryFlatModel>> =
        persistence
            .observeBorders(countryId)
            .map(mapper::mapCountriesFlatToModels)

    private fun observeCallingCodes(countryId: String): Flow<List<String>> =
        callingCodePersistence
            .observeCallingCodes(countryId)
            .map(mapper::mapCallingCodeEntitiesToModels)

    private fun observeCurrencies(countryId: String): Flow<List<CurrencyModel>> =
        currencyPersistence
            .observeCurrencies(countryId)
            .map(mapper::mapCurrencyEntitiesToModels)

    private fun observeLanguages(countryId: String): Flow<List<LanguageModel>> =
        languagePersistence
            .observeLanguages(countryId)
            .map(mapper::mapLanguageEntitiesToModels)

    private fun observeRegionalBlocs(countryId: String): Flow<List<RegionalBlocModel>> =
        regionalBlocPersistence
            .observeRegionalBlocs(countryId)
            .map(mapper::mapRegionalBlocEntitiesToModels)

    private fun observeTimeZones(countryId: String): Flow<List<String>> =
        timeZonePersistence
            .observeTimeZones(countryId)
            .map(mapper::mapTimeZoneEntitiesToModels)

    private fun observeTopLevelDomains(countryId: String): Flow<List<String>> =
        topLevelDomainPersistence
            .observeTopLevelDomains(countryId)
            .map(mapper::mapTopLevelDomainEntitiesToModels)
}