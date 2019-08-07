package pl.valueadd.restcountries.domain.model.country

import org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO
import pl.valueadd.restcountries.network.dto.base.BaseDto

data class LatLngModel(

    var lat: Double = DOUBLE_ZERO,

    var lng: Double = DOUBLE_ZERO

) : BaseDto()