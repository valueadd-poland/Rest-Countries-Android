package pl.valueadd.restcountries.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO

@Entity(tableName = "top_level_domains")
data class TopLevelDomainEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = LONG_ZERO,

    @ColumnInfo(name = "name")
    var name: String

)