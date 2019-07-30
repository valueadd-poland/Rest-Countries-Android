package pl.valueadd.restcountries.utility.parcel

import android.os.Parcel
import org.apache.commons.lang3.StringUtils.EMPTY

fun Parcel.loadString(defaultValue: String = EMPTY): String =
    this.readString() ?: defaultValue