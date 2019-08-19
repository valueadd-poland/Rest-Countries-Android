package pl.valueadd.restcountries.view.chip

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.util.AttributeSet
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import org.apache.commons.lang3.math.NumberUtils
import pl.valueadd.restcountries.domain.model.country.CountryFlatModel
import pl.valueadd.restcountries.utility.image.Options
import pl.valueadd.restcountries.utility.image.listener.SvgSoftwareLayerSetter
import pl.valueadd.restcountries.utility.image.target.ChipTarget

class BorderChip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = NumberUtils.INTEGER_ZERO
) : Chip(context, attrs, defStyleAttr) {

    init {
        isClickable = true
    }

    fun bindModel(model: CountryFlatModel): BorderChip {
        text = model.name
        Glide.with(this)
            .`as`(PictureDrawable::class.java)
            .apply(Options.svgRequest)
            .listener(SvgSoftwareLayerSetter())
            .load(model.flagUrl)
            .into(ChipTarget(this))

        return this
    }
}