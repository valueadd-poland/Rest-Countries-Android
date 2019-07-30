package pl.valueadd.restcountries.domain

import android.app.Application
import pl.valueadd.restcountries.domain.mapper.ExampleMapper
import pl.valueadd.restcountries.network.NetworkModule
import pl.valueadd.restcountries.persistence.PersistenceModule
import org.mapstruct.factory.Mappers
import toothpick.config.Module

class DomainModule : Module() {

    companion object {

        fun provideDependencies(application: Application): Array<Module> {
            return arrayOf(PersistenceModule(application), NetworkModule())
        }
    }

    init {
        // Bind mapper instances.
        bind(ExampleMapper::class.java).toInstance(Mappers.getMapper(ExampleMapper::class.java))
    }
}