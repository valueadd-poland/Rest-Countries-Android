package pl.valueadd.restcountries.domain.model.country

import android.os.Parcel
import android.os.Parcelable
import org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO
import pl.valueadd.restcountries.network.dto.base.BaseDto

data class LatLngModel(

    var lat: Double = DOUBLE_ZERO,

    var lng: Double = DOUBLE_ZERO

) : BaseDto(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LatLngModel> {
        override fun createFromParcel(parcel: Parcel): LatLngModel {
            return LatLngModel(parcel)
        }

        override fun newArray(size: Int): Array<LatLngModel?> {
            return arrayOfNulls(size)
        }
    }
}