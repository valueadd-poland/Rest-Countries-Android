package pl.valueadd.restcountries.persistence.model

import androidx.room.ColumnInfo
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.persistence.entity.CountryEntity.Companion.COL_ALPHA_3_CODE

data class CountryFlat(

    @ColumnInfo(name = "name")
    var name: String = EMPTY,

    @ColumnInfo(name = COL_ALPHA_3_CODE)
    var alpha3Code: String = EMPTY,

    @ColumnInfo(name = "alpha2_code")
    var alpha2Code: String = EMPTY,

    @ColumnInfo(name = "flag_url")
    var flagUrl: String = EMPTY

)