package pl.valueadd.restcountries.network.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO
import pl.valueadd.restcountries.network.dto.country.LatLngDto
import java.lang.reflect.Type

class LatLngDeserializer : JsonDeserializer<LatLngDto> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): LatLngDto =
        json.asJsonArray.run {
            val lat: Double = if (size() > 0) {
                get(0).asDouble
            } else DOUBLE_ZERO
            val lng: Double = if (size() > 1) {
                get(1).asDouble
            } else DOUBLE_ZERO
            return LatLngDto(lat, lng)
        }
}