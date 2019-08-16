package pl.valueadd.restcountries.domain.model.language

import android.os.Parcel
import android.os.Parcelable
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.domain.model.base.BaseModel
import pl.valueadd.restcountries.utility.common.appendComma
import pl.valueadd.restcountries.utility.parcel.loadString

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

) : BaseModel(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString(),
        parcel.loadString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(de)
        parcel.writeString(es)
        parcel.writeString(fr)
        parcel.writeString(ja)
        parcel.writeString(it)
        parcel.writeString(br)
        parcel.writeString(pt)
        parcel.writeString(nl)
        parcel.writeString(hr)
        parcel.writeString(fa)
    }

    override fun describeContents(): Int {
        return 0
    }

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

    companion object CREATOR : Parcelable.Creator<TranslationsModel> {
        override fun createFromParcel(parcel: Parcel): TranslationsModel {
            return TranslationsModel(parcel)
        }

        override fun newArray(size: Int): Array<TranslationsModel?> {
            return arrayOfNulls(size)
        }
    }
}