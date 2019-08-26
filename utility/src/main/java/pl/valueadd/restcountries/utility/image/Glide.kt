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
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import pl.valueadd.restcountries.utility.image.listener.SvgSoftwareLayerSetter

object Options {
    val svgRequest: RequestOptions by lazy {
        RequestOptions().apply {
            skipMemoryCache(true)
            dontTransform()
            override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
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

fun <T : ImageView> T.loadSVGImage(
    url: String,
    @DrawableRes placeholder: Int = View.NO_ID,
    @ColorInt placeholderColorInt: Int = Color.WHITE,
    listener: RequestListener<PictureDrawable> = emptyResourceListener()
) {
    val drawable: Drawable? =
        if (placeholder != View.NO_ID) {
            ContextCompat.getDrawable(context, placeholder)?.apply {
                setTint(placeholderColorInt)
            }
        } else null

    Glide.with(this)
        .`as`(PictureDrawable::class.java)
        .placeholder(drawable)
        .apply(Options.svgRequest)
        .listener(SvgSoftwareLayerSetter())
        .listener(listener)
        .load(url)
        .into(this)
}

fun <T : ImageView> T.clearImage() {
    GlideApp.with(context.applicationContext).clear(this)
    this.setImageDrawable(null)
}

inline fun <R> onResourceReady(
    crossinline action: RequestListener<R>.(resource: R, model: Any?, target: Target<R>?, dataSource: DataSource?, isFirstResource: Boolean) -> Unit
) =
    object : RequestListener<R> {

        override fun onResourceReady(resource: R, model: Any?, target: Target<R>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            action(resource, model, target, dataSource, isFirstResource)
            return false
        }

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<R>?, isFirstResource: Boolean): Boolean {
            // no-op

            return false
        }
    }

private fun <R> emptyResourceListener(): RequestListener<R> =
    onResourceReady { _, _, _, _, _ -> /* EMPTY */ }