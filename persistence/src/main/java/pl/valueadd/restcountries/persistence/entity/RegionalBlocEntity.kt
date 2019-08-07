package pl.valueadd.restcountries.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.apache.commons.lang3.StringUtils.EMPTY

@Entity(tableName = "regional_blocks")
data class RegionalBlocEntity(

    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String = EMPTY,

    @ColumnInfo(name = "acronym")
    var acronym: String = EMPTY

//    @ColumnInfo(name = "otherAcronyms")
//    var otherAcronyms: List<String> = emptyList(),
//
//    @ColumnInfo(name = "otherNames")
//    var otherNames: List<String> = emptyList()

) {

    var id: String
        get() = name
        set(value) {
            name = value
        }
}