package pl.valueadd.restcountries.utility.image

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.caverock.androidsvg.SVG
import pl.valueadd.restcountries.utility.image.decoder.SvgDecoder
import pl.valueadd.restcountries.utility.image.transcoder.SvgDrawableTranscoder
import java.io.InputStream
import javax.inject.Inject

@GlideModule
class GlideSettings @Inject constructor() : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean = false

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry
            .register(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
            .append(InputStream::class.java, SVG::class.java, SvgDecoder())
    }
}