package pl.valueadd.restcountries.domain.model.language

import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel

class LanguageModel(
    
    var iso6391: String = EMPTY,
    
    var iso6392: String = EMPTY,
    
    var name: String = EMPTY,

    var nativeName: String = EMPTY
    
) : BaseModel()