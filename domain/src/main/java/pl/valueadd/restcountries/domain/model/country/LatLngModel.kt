package pl.valueadd.restcountries.domain.model.country

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO
import pl.valueadd.restcountries.domain.model.base.BaseModel

@Parcelize
data class LatLngModel(

    var lat: Double = DOUBLE_ZERO,

    var lng: Double = DOUBLE_ZERO

) : BaseModel(), Parcelable {

    val hasNotDefaultValues: Boolean
        get() = lat != DOUBLE_ZERO || lng != DOUBLE_ZERO
}