package pl.valueadd.restcountries.utility.view

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import kotlinx.coroutines.CoroutineScope
import pl.valueadd.restcountries.utility.coroutines.throttleFirst

fun View.setVisible(isVisible: Boolean, falseRes: Int = View.GONE) {

    val visibility = if (isVisible) View.VISIBLE else falseRes

    this.visibility = visibility
}

fun ViewGroup.getChildAtOrNull(index: Int): View? {
    return if (index < childCount) {
        getChildAt(index)
    } else null
}

fun Menu.setTextColor(@IdRes idRes: Int, @ColorInt color: Int) {
    this.findItem(idRes)?.apply {
        val s = SpannableString(title)
        s.setSpan(ForegroundColorSpan(color), 0, s.length, 0)
        title = s
    }
}

fun Menu.show(@IdRes idRes: Int) {
    this.findItem(idRes)?.isVisible = true
}

fun SearchView.applyAligmentToTheRight() {
    layoutParams = Toolbar.LayoutParams(Gravity.END)
}

fun View.setOnClickListener(listener: () -> Unit) {
    setOnClickListener { listener() }
}

inline fun View.throttleClicks(scope: CoroutineScope, crossinline onClick: () -> Unit) {
    val onClickListener = scope.throttleFirst {
        onClick()
    }
    setOnClickListener(onClickListener)
}