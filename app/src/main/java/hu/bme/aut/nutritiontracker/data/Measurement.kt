package hu.bme.aut.nutritiontracker.data

data class Measurement(
    val name: String,
    val currentSize: Double?,
    val previousSize: Double?
)
