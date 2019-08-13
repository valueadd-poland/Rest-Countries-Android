package pl.valueadd.restcountries.utility.image.target

import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.chip.Chip

class ChipTarget(view: Chip) : CustomViewTarget<Chip, PictureDrawable>(view) {

    override fun onLoadFailed(errorDrawable: Drawable?) {
        //no-op
    }

    override fun onResourceCleared(placeholder: Drawable?) {
        //no-op
    }

    override fun onResourceReady(resource: PictureDrawable, transition: Transition<in PictureDrawable>?) {
        view.chipIcon = resource
    }
}