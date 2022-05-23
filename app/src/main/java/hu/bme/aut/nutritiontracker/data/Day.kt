package hu.bme.aut.nutritiontracker.data

import java.sql.Timestamp
import java.util.*

data class Day(
    var parentId: String? = null,
    var day: Date = Date(),
    var consumedFood: List<ConsumedFood> = emptyList(),
    var kcalLimit: Int = 0,
    var measurement: List<Measurement> = emptyList(),
    var macroTotal: MacroNutrition = MacroNutrition()
)
