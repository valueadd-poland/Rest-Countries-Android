package pl.valueadd.restcountries.utility.image.listener

import android.graphics.drawable.PictureDrawable
import android.view.View
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.Target

class SvgSoftwareLayerSetter : RequestListener<PictureDrawable> {

    override fun onResourceReady(resource: PictureDrawable, model: Any?, target: Target<PictureDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
        if (target is ImageViewTarget<*>) {
            target.view.setSoftwareLayer()
        }
        if (target is CustomViewTarget<*, *>) {
            target.view.setSoftwareLayer()
        }
        return false
    }

    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<PictureDrawable>?, isFirstResource: Boolean): Boolean {
        if (target is ImageViewTarget<*>) {
            target.view.setNoneLayer()
        }
        if (target is CustomViewTarget<*, *>) {
            target.view.setNoneLayer()
        }
        return false
    }

    private fun View.setSoftwareLayer() {
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    private fun View.setNoneLayer() {
        this.setLayerType(View.LAYER_TYPE_NONE, null)
    }
}