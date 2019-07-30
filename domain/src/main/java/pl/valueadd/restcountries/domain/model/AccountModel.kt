package pl.valueadd.restcountries.domain.model

import android.os.Parcel
import android.os.Parcelable
import pl.valueadd.restcountries.domain.model.base.BaseModel
import pl.valueadd.restcountries.utility.parcel.loadString
import org.apache.commons.lang3.StringUtils.EMPTY

data class AccountModel(

    var firstName: String = EMPTY,

    var surname: String = EMPTY,

    var email: String = EMPTY

) : BaseModel(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(surname)
        parcel.writeString(email)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AccountModel> {

        override fun createFromParcel(parcel: Parcel): AccountModel {
            return AccountModel(parcel)
        }

        override fun newArray(size: Int): Array<AccountModel?> {
            return arrayOfNulls(size)
        }
    }
}