package pl.valueadd.restcountries.domain.model.country

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel

@Parcelize
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
}