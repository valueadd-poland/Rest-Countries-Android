package pl.valueadd.restcountries.domain.model.country

import android.os.Parcel
import android.os.Parcelable
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel
import pl.valueadd.restcountries.utility.parcel.loadString

data class CountryFlatModel(

    var name: String = EMPTY,

    var alpha3Code: String = EMPTY,

    var alpha2Code: String = EMPTY,

    var flagUrl: String = EMPTY

) : BaseModel(), Parcelable {

    var id: String
        get() = alpha3Code
        set(value) {
            alpha3Code = value
        }

    constructor(parcel: Parcel) : this(
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(alpha3Code)
        parcel.writeString(alpha2Code)
        parcel.writeString(flagUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryFlatModel> {
        override fun createFromParcel(parcel: Parcel): CountryFlatModel {
            return CountryFlatModel(parcel)
        }

        override fun newArray(size: Int): Array<CountryFlatModel?> {
            return arrayOfNulls(size)
        }
    }

}