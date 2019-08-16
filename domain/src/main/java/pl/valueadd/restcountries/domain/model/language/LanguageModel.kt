package pl.valueadd.restcountries.domain.model.language

import android.os.Parcel
import android.os.Parcelable
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel
import pl.valueadd.restcountries.utility.parcel.loadString

class LanguageModel(

    var iso6391: String = EMPTY,

    var iso6392: String = EMPTY,

    var name: String = EMPTY,

    var nativeName: String = EMPTY

) : BaseModel(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(iso6391)
        parcel.writeString(iso6392)
        parcel.writeString(name)
        parcel.writeString(nativeName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LanguageModel> {
        override fun createFromParcel(parcel: Parcel): LanguageModel {
            return LanguageModel(parcel)
        }

        override fun newArray(size: Int): Array<LanguageModel?> {
            return arrayOfNulls(size)
        }
    }

}