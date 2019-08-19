package pl.valueadd.restcountries.utility.image.decoder

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import java.io.IOException
import java.io.InputStream

internal class SvgDecoder : ResourceDecoder<InputStream, SVG> {

    override fun decode(source: InputStream, width: Int, height: Int, options: Options): Resource<SVG>? {
        val svg: SVG

        try {
            svg = SVG.getFromInputStream(source)
        } catch (e: SVGParseException) {
            throw IOException("Cannot load SVG from source.", e)
        }

        return SimpleResource(svg)
    }

    override fun handles(source: InputStream, options: Options): Boolean =
        true
}