package pl.valueadd.restcountries.utility.view.snackbar

import android.app.Activity
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import pl.valueadd.restcountries.utility.R
import javax.inject.Inject

/**
 * Created by Arkadiusz Pałka on 13/08/2018.
 */
class SnackbarUtil @Inject constructor(private val activity: Activity) {

    private var snackbar: Snackbar? = null
    private val rootView: View
        get() = activity.window.decorView.findViewById(android.R.id.content)

    private fun isShowing() = snackbar?.isShown ?: false

    fun showMessage(
        view: View = rootView,
        message: String,
        @ColorInt messageColor: Int? = null,
        duration: Int = Snackbar.LENGTH_SHORT,
        callback: Snackbar.Callback? = null,
        actionText: String? = null,
        @ColorInt actionColor: Int? = null,
        actionListener: View.OnClickListener? = null
    ): Snackbar? {

        if (isShowing()) {
            dismiss()
        }

        snackbar = Snackbar.make(view, message, duration)
            .setAction(actionText, actionListener)

        callback?.let {
            snackbar?.addCallback(it)
        }
        actionColor?.let {
            snackbar?.setActionTextColor(it)
        }
        messageColor?.let {
            snackbar?.view?.findViewById<TextView>(R.id.snackbar_text)?.setTextColor(it)
        }

        snackbar?.show()
        return snackbar
    }

    fun dismiss() {
        snackbar?.dismiss()
        snackbar = null
    }

    fun showMessage(
        view: View = rootView,
        @StringRes message: Int,
        @ColorInt messageColor: Int? = null,
        duration: Int = Snackbar.LENGTH_SHORT,
        callback: Snackbar.Callback? = null,
        actionText: String? = null,
        @ColorInt actionColor: Int? = null,
        actionListener: View.OnClickListener? = null
    ): Snackbar? {

        return showMessage(view, activity.getString(message), messageColor, duration, callback, actionText, actionColor, actionListener)
    }
}