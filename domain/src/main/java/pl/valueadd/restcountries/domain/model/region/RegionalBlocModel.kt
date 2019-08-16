package pl.valueadd.restcountries.domain.model.region

import android.os.Parcel
import android.os.Parcelable
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel
import pl.valueadd.restcountries.utility.parcel.createStringList
import pl.valueadd.restcountries.utility.parcel.loadString

data class RegionalBlocModel(

    var acronym: String = EMPTY,

    var name: String = EMPTY,

    var otherAcronyms: List<String> = emptyList(),

    var otherNames: List<String> = emptyList()

) : BaseModel(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.loadString(),
        parcel.loadString(),
        parcel.createStringList(),
        parcel.createStringList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(acronym)
        parcel.writeString(name)
        parcel.writeStringList(otherAcronyms)
        parcel.writeStringList(otherNames)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RegionalBlocModel> {
        override fun createFromParcel(parcel: Parcel): RegionalBlocModel {
            return RegionalBlocModel(parcel)
        }

        override fun newArray(size: Int): Array<RegionalBlocModel?> {
            return arrayOfNulls(size)
        }
    }
}