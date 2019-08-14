package pl.valueadd.restcountries.utility.image.target

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
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

        val resourceBitmap: Bitmap = Bitmap.createBitmap(resource.intrinsicWidth, resource.intrinsicHeight, Bitmap.Config.ARGB_8888)

        Canvas(resourceBitmap).apply {
            drawPicture(resource.picture)
        }

        val chipIconSize: Int = view.chipIconSize.toInt()
        val centerSide: Int
        val centerX: Int
        val centerY: Int

        if (resource.intrinsicWidth >= resource.intrinsicHeight) {
            centerSide = resource.intrinsicHeight
            centerX = resource.intrinsicWidth / 2 - resource.intrinsicHeight / 2
            centerY = 0
        } else {
            centerSide = resource.intrinsicWidth
            centerX = 0
            centerY = resource.intrinsicHeight / 2 - resource.intrinsicWidth / 2
        }

        val centeredBitmap: Bitmap = Bitmap.createBitmap(resourceBitmap, centerX, centerY, centerSide, centerSide)

        resourceBitmap.recycle()

        val scaledBitmap: Bitmap = Bitmap.createScaledBitmap(centeredBitmap, chipIconSize, chipIconSize, true)

        centeredBitmap.recycle()

        val desiredDrawable: Drawable = RoundedBitmapDrawableFactory.create(view.resources, scaledBitmap).apply {
            isCircular = true
        }

        view.chipIcon = desiredDrawable
    }
}