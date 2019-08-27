package pl.valueadd.restcountries.view.chip

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.chip.Chip
import org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO

class ActionableChip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = INTEGER_ZERO
) : Chip(context, attrs, defStyleAttr) {

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        setOnCloseIconClickListener(l)
    }
}