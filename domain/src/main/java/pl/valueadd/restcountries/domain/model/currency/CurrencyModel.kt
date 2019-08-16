package pl.valueadd.restcountries.domain.model.currency

import android.os.Parcel
import android.os.Parcelable
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel
import pl.valueadd.restcountries.utility.parcel.loadString

data class CurrencyModel(

    var code: String = EMPTY,

    var name: String = EMPTY,

    var symbol: String = EMPTY

) : BaseModel(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeString(symbol)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrencyModel> {
        override fun createFromParcel(parcel: Parcel): CurrencyModel {
            return CurrencyModel(parcel)
        }

        override fun newArray(size: Int): Array<CurrencyModel?> {
            return arrayOfNulls(size)
        }
    }
}