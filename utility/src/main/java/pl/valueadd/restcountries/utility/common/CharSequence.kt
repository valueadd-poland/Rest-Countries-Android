package pl.valueadd.restcountries.utility.common

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan

fun CharSequence.boldText(): CharSequence {
    return SpannableStringBuilder(this).apply {
        setSpan(StyleSpan(Typeface.BOLD), 0, this.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }
}

fun StringBuilder.appendComma() {
    if (this.isNotBlank()) {
        this.append(", ")
    }
}

fun List<String>.merge(separator: Char = ','): String {
    return StringBuilder().apply {
        for (str in this@merge) {
            if (!str.isBlank()) {
                if (this.isNotBlank()) {
                    this.append("$separator ")
                }
                append(str)
            }
        }
    }.toString()
}