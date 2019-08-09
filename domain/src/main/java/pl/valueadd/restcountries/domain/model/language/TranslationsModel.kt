package pl.valueadd.restcountries.domain.model.language

import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel
import pl.valueadd.restcountries.utility.common.appendComma

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

) : BaseModel() {

    override fun toString(): String {
        return StringBuilder().apply {
            arrayOf(de, es, fr, ja, it, br, pt, nl, hr, fa).forEach {
                if (!it.isBlank()) {
                    appendComma()
                    append(it)
                }
            }
        }.toString()
    }
}