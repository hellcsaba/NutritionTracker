package hu.bme.aut.nutritiontracker.data

data class ConsumedFood(
    var id: String? = null,
    var name: String = "",
    var amount: Int? = null,
    var kcal: Int? = null,
    var protein: Int? = null,
    var carb: Int? = null,
    var fat: Int? = null,
    var image: String? = null
)
