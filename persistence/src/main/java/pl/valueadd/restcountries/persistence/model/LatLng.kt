package pl.valueadd.restcountries.persistence.model

import androidx.room.ColumnInfo
import org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO

data class LatLng(

    @ColumnInfo(name = "lat")
    var lat: Double = DOUBLE_ZERO,

    @ColumnInfo(name = "lng")
    var lng: Double = DOUBLE_ZERO

)