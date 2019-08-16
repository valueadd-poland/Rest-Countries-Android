package pl.valueadd.restcountries.persistence.model

import androidx.room.ColumnInfo
import org.apache.commons.lang3.StringUtils.EMPTY

data class Border(

    @ColumnInfo(name = "name")
    var name: String = EMPTY

) {

    var id: String
        get() = name
        set(value) {
            name = value
        }

}