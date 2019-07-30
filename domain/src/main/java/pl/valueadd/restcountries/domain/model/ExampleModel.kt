package pl.valueadd.restcountries.domain.model

import pl.valueadd.restcountries.domain.model.base.BaseModel
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils

class ExampleModel(

    var id: Long = NumberUtils.LONG_ZERO,

    var title: String = StringUtils.EMPTY

) : BaseModel()
