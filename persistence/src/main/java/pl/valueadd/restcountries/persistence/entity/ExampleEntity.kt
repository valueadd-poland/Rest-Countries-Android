package pl.valueadd.restcountries.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO

@Entity(tableName = "examples")
data class ExampleEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long = LONG_ZERO,

    var title: String = EMPTY
)