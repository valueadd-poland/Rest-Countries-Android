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

/**
 * Appends [separator] only when is needed ([StringBuilder] is not blank)
 */
fun StringBuilder.appendComma(separator: Char = ',') {
    if (this.isNotBlank()) {
        this.append("$separator ")
    }
}

/**
 * Concat the [String]s through [separator] and empty space.
 */
fun List<String>.merge(separator: Char = ','): String {
    return StringBuilder().apply {
        for (str in this@merge) {
            if (!str.isBlank()) {
                appendComma(separator)
                append(str)
            }
        }
    }.toString()
}