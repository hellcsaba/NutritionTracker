package hu.bme.aut.nutritiontracker.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Measurement(
    val name: String = "",
    val currentSize: Double? = null,
    val previousSize: Double? = null
)
