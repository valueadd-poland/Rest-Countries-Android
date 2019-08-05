package pl.valueadd.restcountries.domain.model.language

import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel

data class TranslationsModel(
    
    var de: String = EMPTY,
    
    var es: String = EMPTY,

    var fr: String = EMPTY,

    var ja: String = EMPTY,

    var it: String = EMPTY,

    var br: String = EMPTY,

    var pt: String = EMPTY,

    var nl: String = EMPTY,

    var hr: String = EMPTY,
    
    var fa: String = EMPTY
    
) : BaseModel()