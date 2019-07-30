package pl.valueadd.restcountries.utility.image

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun <T : ImageView> T.loadImage(url: String, @DrawableRes placeholder: Int = View.NO_ID) {
    GlideApp.with(this)
        .load(url)
        .placeholder(placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun <T : ImageView> T.clearImage() {
    GlideApp.with(this).clear(this)
    this.setImageDrawable(null)
}