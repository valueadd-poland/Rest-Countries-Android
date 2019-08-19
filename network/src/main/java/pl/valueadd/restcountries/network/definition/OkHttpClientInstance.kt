package pl.valueadd.restcountries.network.definition

import javax.inject.Qualifier

@Target(AnnotationTarget.EXPRESSION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@Qualifier
internal annotation class OkHttpClientInstance