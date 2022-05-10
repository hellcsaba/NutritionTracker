package hu.bme.aut.nutritiontracker.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeResult (
    val results      : List<Recipe>?,
    val offset       : Int?,
    val number       : Int?,
    val totalResults : Int?
)

@JsonClass(generateAdapter = true)
data class Recipe(
    val id        : Int?,
    val title     : String?,
    val image     : String?,
    val imageType : String?

)