package pl.valueadd.restcountries.domain.model.currency

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel

@Parcelize
data class CurrencyModel(

    var code: String = EMPTY,

    var name: String = EMPTY,

    var symbol: String = EMPTY

) : BaseModel(), Parcelable