package pl.valueadd.restcountries.domain.manager

import io.reactivex.Completable
import io.reactivex.Flowable
import pl.valueadd.restcountries.domain.mapper.CountryMapper
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.network.dto.country.CountryDto
import pl.valueadd.restcountries.network.manager.CountryNetworkManager
import pl.valueadd.restcountries.persistence.manager.CountryPersistenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryDomainManager @Inject constructor(
    private val network: CountryNetworkManager,
    private val persistence: CountryPersistenceManager,
    private val mapper: CountryMapper
) {

    fun downloadAllCountries(): Completable =
        network
            .getAllCountries()
            .flatMapCompletable {
                persistence.saveCountries(mapper.mapCountryDtosToEntities(it))
                    .andThen()
            }

    fun observeAllCountries(): Flowable<List<CountryModel>> =
        persistence
            .observeAllCountries()
            .map(mapper::mapCountryEntitiesToModels)

    private fun saveCountries(list: List<CountryDto>): Completable =
        Completable.concat(
            persistence.saveCountries(mapper.mapCountryDtosToEntities(list),
                persistence.
        )
}