package pl.valueadd.restcountries.domain.manager

import pl.valueadd.restcountries.network.manager.ExceptionNetworkManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExceptionDomainManager @Inject constructor(private val networkManager: ExceptionNetworkManager) {

    fun mapToMessage(throwable: Throwable) = networkManager.mapToMessage(throwable)
}