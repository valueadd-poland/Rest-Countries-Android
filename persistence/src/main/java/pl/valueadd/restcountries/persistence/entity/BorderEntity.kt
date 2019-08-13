package pl.valueadd.restcountries.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO

@Entity(tableName = "borders")
data class BorderEntity(

    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String = EMPTY

) {

    var id: String
        get() = name
        set(value) {
            name = value
        }

}