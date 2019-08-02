package pl.valueadd.restcountries.persistence.manager

import io.reactivex.annotations.SchedulerSupport
import javax.inject.Singleton

@Singleton
@SchedulerSupport(value = SchedulerSupport.IO)
class CountryPersistenceManager {
}