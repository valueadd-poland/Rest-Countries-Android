package pl.valueadd.restcountries.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.property_view.view.propertyImageView
import kotlinx.android.synthetic.main.property_view.view.propertySubtitleTextView
import kotlinx.android.synthetic.main.property_view.view.propertyTitleTextView
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO
import pl.valueadd.restcountries.R

class PropertyView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = INTEGER_ZERO
) : AbstractLinearLayoutView(context, attrs, defStyleAttr, R.layout.property_view) {

    var title: String
        get() = propertyTitleTextView.text.toString()
        set(value) = propertyTitleTextView.setText(value)

    var subtitle: String
        get() = propertySubtitleTextView.text.toString()
        set(value) = propertySubtitleTextView.setText(value)

    @ColorRes
    private var iconHighlightColor: Int = INTEGER_ZERO
        set(value) {
            field = value

            setImageIconTint(value)
        }

    @DrawableRes
    private var iconDrawableRes: Int = INTEGER_ZERO
        set(value) {
            field = value

            setImageIcon(value)
        }

    override val layoutOrientation: Int
        get() = HORIZONTAL

    override val layoutWidth: Int
        get() = LayoutParams.MATCH_PARENT

    override val layoutHeight: Int
        get() = LayoutParams.MATCH_PARENT

    override fun collectAttrs() {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PropertyView)

        iconDrawableRes = typedArray.getResourceId(R.styleable.PropertyView_iconDrawable, View.NO_ID)
        iconHighlightColor = typedArray.getResourceId(R.styleable.PropertyView_iconHighlightColor, View.NO_ID)
        title = typedArray.getString(R.styleable.PropertyView_title) ?: EMPTY
        subtitle = typedArray.getString(R.styleable.PropertyView_subtitle) ?: EMPTY

        typedArray.recycle()
    }

    private fun setImageIcon(@DrawableRes resId: Int) {
        if (resId == View.NO_ID) return

        val drawable = ContextCompat.getDrawable(context, iconDrawableRes)

        propertyImageView.setImageDrawable(drawable)
    }

    private fun setImageIconTint(@ColorRes resId: Int) {
        if (resId == View.NO_ID) return

        val drawableColor = ContextCompat.getColor(context, iconHighlightColor)

        propertyImageView.drawable?.mutate()?.setTint(drawableColor)
    }
}