package pl.valueadd.restcountries.domain.model.region

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel

@Parcelize
data class RegionalBlocModel(

    var acronym: String = EMPTY,

    var name: String = EMPTY,

    var otherAcronyms: List<String> = emptyList(),

    var otherNames: List<String> = emptyList()

) : BaseModel(), Parcelable