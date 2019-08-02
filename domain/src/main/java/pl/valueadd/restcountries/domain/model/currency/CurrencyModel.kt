package pl.valueadd.restcountries.domain.model.currency

import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel

data class CurrencyModel(

    var code: String = EMPTY,

    var name: String = EMPTY,

    var symbol: String = EMPTY

) : BaseModel()