package hu.bme.aut.nutritiontracker.data

data class User (
    var name: String = "",
    var email: String = "",
    var day: List<Day> = emptyList(),
    var bodyComposition: BodyComposition = BodyComposition(),
    var macroTotal: MacroNutrition = MacroNutrition(),
    var consumedFood: List<ConsumedFood> = emptyList(),
    var kcalLimit: Int = 0
)