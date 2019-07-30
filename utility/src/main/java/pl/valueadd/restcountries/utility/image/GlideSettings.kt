package pl.valueadd.restcountries.utility.image

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import javax.inject.Inject

@GlideModule
class GlideSettings @Inject constructor() : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean = false
}