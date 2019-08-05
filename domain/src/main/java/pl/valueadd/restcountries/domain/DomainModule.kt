package pl.valueadd.restcountries.domain

import android.app.Application
import pl.valueadd.restcountries.network.NetworkModule
import pl.valueadd.restcountries.persistence.PersistenceModule
import org.mapstruct.factory.Mappers
import pl.valueadd.restcountries.domain.mapper.CountryMapper
import toothpick.config.Module

class DomainModule : Module() {

    companion object {

        fun provideDependencies(application: Application): Array<Module> {
            return arrayOf(PersistenceModule(application), NetworkModule())
        }
    }

    init {
        // Bind mapper instances.
        bind(CountryMapper::class.java).toInstance(Mappers.getMapper(CountryMapper::class.java))
    }
}