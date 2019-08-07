package pl.valueadd.restcountries.network.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO
import pl.valueadd.restcountries.network.dto.country.LatLngDto
import java.lang.reflect.Type

class LatLngTypeAdapter : TypeAdapter<LatLngDto>(), JsonDeserializer<LatLngDto> {

    companion object {
        private const val LAT_INDEX = 0
        private const val LNG_INDEX = 1
    }

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): LatLngDto =
        json.asJsonArray.run {
            val lat: Double = if (size() > 0) {
                get(LAT_INDEX).asDouble
            } else DOUBLE_ZERO
            val lng: Double = if (size() > 1) {
                get(LNG_INDEX).asDouble
            } else DOUBLE_ZERO
            return LatLngDto(lat, lng)
        }

    override fun write(out: JsonWriter, dto: LatLngDto?) {
        out.beginArray()
            .value(dto?.lat)
            .value(dto?.lng)
            .endArray()
    }

    override fun read(reader: JsonReader): LatLngDto = reader.run {
        beginArray()
        val lat: Double = if (hasNext()) {
            nextDouble()
        } else DOUBLE_ZERO
        val lng: Double = if (hasNext()) {
            nextDouble()
        } else DOUBLE_ZERO
        endArray()
        LatLngDto(lat = lat, lng = lng)
    }
}