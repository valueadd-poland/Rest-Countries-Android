package pl.valueadd.restcountries.domain.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import pl.valueadd.restcountries.domain.mapper.CountryMapper
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.network.dto.country.CountryDto
import pl.valueadd.restcountries.network.manager.CountryNetworkManager
import pl.valueadd.restcountries.persistence.entity.AltSpellingEntity
import pl.valueadd.restcountries.persistence.entity.CallingCodeEntity
import pl.valueadd.restcountries.persistence.entity.CountryEntity
import pl.valueadd.restcountries.persistence.entity.CurrencyEntity
import pl.valueadd.restcountries.persistence.entity.LanguageEntity
import pl.valueadd.restcountries.persistence.entity.RegionalBlocEntity
import pl.valueadd.restcountries.persistence.entity.TimeZoneEntity
import pl.valueadd.restcountries.persistence.entity.TopLevelDomainEntity
import pl.valueadd.restcountries.persistence.entity.join.CountryAltSpellingJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryCallingCodeJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryCurrencyJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryLanguageJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryRegionalBlocJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryTimeZoneJoin
import pl.valueadd.restcountries.persistence.entity.join.CountryTopLevelDomainJoin
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

    fun observeAllCountries(): Flowable<List<CountryModel>> =
        persistence
            .observeAllCountries()
            .map(mapper::mapCountryEntitiesToModels)

    fun downloadAllCountries(): Completable =
        network
            .getAllCountries()
            .flatMapCompletable(::saveAllCountries)

    private fun saveAllCountries(list: List<CountryDto>): Completable {
        val saveTasks: MutableList<Completable> = mutableListOf()

        return immediateSingle {
            mutableListOf<CountryEntity>().also { entities ->
                for (dto in list) {

                    val entity: CountryEntity = mapper.mapCountryDtoToEntity(dto)

                    saveTasks.add(saveCallingCodesFor(entity.id, mapper.mapCallingCodeDtosToEntities(dto.callingCodes)))
                    saveTasks.add(saveTopLevelDomainsFor(entity.id, mapper.mapTopLevelDomainDtosToEntities(dto.topLevelDomain)))
                    saveTasks.add(saveAltSpellingsFor(entity.id, mapper.mapAltSpellingDtosToEntities(dto.altSpellings)))
                    saveTasks.add(saveTimeZonesFor(entity.id, mapper.mapTimeZoneDtosToEntities(dto.timezones)))
                    saveTasks.add(saveCurrenciesFor(entity.id, mapper.mapCurrencyDtosToEntities(dto.currencies)))
                    saveTasks.add(saveLanguagesFor(entity.id, mapper.mapLanguageDtosToEntities(dto.languages)))
                    saveTasks.add(saveRegionalBlocsFor(entity.id, mapper.mapRegionalBlocDtosToEntities(dto.regionalBlocs)))

                    entities.add(entity)
                }
            }
        }.flatMapCompletable(persistence::saveCountries)
            .andThen(Completable.merge(saveTasks))
    }

    private fun saveCallingCodesFor(countryId: String, entities: List<CallingCodeEntity>): Completable =
        callingCodePersistence.saveCallingCodesIds(entities)
            .map { list ->
                list.map { CountryCallingCodeJoin(countryId, it) }
            }.flatMapCompletable(persistence::saveCountryCallingCodeJoins)

    private fun saveCurrenciesFor(countryId: String, entities: List<CurrencyEntity>): Completable {
        val map: Single<List<CountryCurrencyJoin>> = immediateSingle {
            entities.map { CountryCurrencyJoin(countryId, it.id) }
        }

        return currencyPersistence.saveCurrencies(entities)
            .andThen(map)
            .flatMapCompletable(persistence::saveCountryCurrencyJoins)
    }

    private fun saveLanguagesFor(countryId: String, entities: List<LanguageEntity>): Completable {
        val map: Single<List<CountryLanguageJoin>> = immediateSingle {
            entities.map { CountryLanguageJoin(countryId, it.id) }
        }

        return languagePersistence.saveLanguages(entities)
            .andThen(map)
            .flatMapCompletable(persistence::saveCountryLanguageJoins)
    }

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

    private fun saveRegionalBlocsFor(countryId: String, entities: List<RegionalBlocEntity>): Completable {
        val map: Single<List<CountryRegionalBlocJoin>> = immediateSingle {
            entities.map { CountryRegionalBlocJoin(countryId, it.id) }
        }

        return regionalBlocPersistence.saveRegionalBlocs(entities)
            .andThen(map)
            .flatMapCompletable(persistence::saveCountryRegionalBlocJoins)
    }
}