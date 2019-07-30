package pl.valueadd.restcountries.utility.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessToken @Inject constructor() {

    var code: String = ""

    fun clear() {
        code = ""
    }
}
