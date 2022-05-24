package hu.bme.aut.nutritiontracker.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Measurement(
    var id: String? = null,
    val name: String = "",
    val currentSize: Double? = null,
    val previousSize: Double? = null
)
