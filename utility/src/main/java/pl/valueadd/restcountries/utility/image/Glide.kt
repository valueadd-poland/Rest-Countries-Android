package pl.valueadd.restcountries.utility.image

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import pl.valueadd.restcountries.utility.image.listener.SvgSoftwareLayerSetter

object Options {
    val svgRequest: RequestOptions by lazy {
        RequestOptions().apply {
            diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontTransform()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        }
    }
}

fun <T : ImageView> T.loadImage(url: String, @DrawableRes placeholder: Int = View.NO_ID, @ColorInt colorInt: Int = Color.WHITE) {
    val drawable: Drawable? = ContextCompat.getDrawable(context, placeholder)
    loadImage(url, drawable, colorInt)
}

fun <T : ImageView> T.loadImage(url: String, placeholder: Drawable? = null, @ColorInt colorInt: Int = Color.WHITE) {
    placeholder?.setTint(colorInt)

    GlideApp.with(this)
        .load(url)
        .placeholder(placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun <T : ImageView> T.loadSVGImage(url: String, @DrawableRes placeholder: Int = View.NO_ID, @ColorInt placeholderColorInt: Int = Color.WHITE) {
    val drawable: Drawable? = ContextCompat.getDrawable(context, placeholder)
    loadSVGImage(url, drawable, placeholderColorInt)
}

fun <T : ImageView> T.loadSVGImage(url: String, placeholder: Drawable? = null, @ColorInt placeholderColorInt: Int = Color.WHITE) {
    placeholder?.setTint(placeholderColorInt)

    Glide.with(this)
        .`as`(PictureDrawable::class.java)
        .placeholder(placeholder)
        .apply(Options.svgRequest)
        .listener(SvgSoftwareLayerSetter())
        .load(url)
        .into(this)
}

fun <T : ImageView> T.clearImage() {
    GlideApp.with(this).clear(this)
    this.setImageDrawable(null)
}