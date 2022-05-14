package hu.bme.aut.nutritiontracker.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeListResult (
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

@JsonClass(generateAdapter = true)
data class ExtendedIngredient(
    val aisle: String?,
    val amount: Double?,
    val consitency: String?,
    val id: Int,
    val image: String?,
    val measures: Measures?,
    val meta: List<String>?,
    val name: String?,
    val original: String?,
    val originalName: String?,
    val unit: String?
)

@JsonClass(generateAdapter = true)
data class Measures(
    val metric: Metric?,
    val us: Us?
)

@JsonClass(generateAdapter = true)
data class Metric(
    val amount: Double?,
    val unitLong: String?,
    val unitShort: String?
)

@JsonClass(generateAdapter = true)
data class ProductMatche(
    val averageRating: Double?,
    val description: String?,
    val id: Int,
    val imageUrl: String?,
    val link: String?,
    val price: String?,
    val ratingCount: Double?,
    val score: Double?,
    val title: String?
)

@JsonClass(generateAdapter = true)
data class RecipeDetailResult(
    val aggregateLikes: Int?,
    val analyzedInstructions: List<Any>?,
    val cheap: Boolean?,
    val creditsText: String?,
    val cuisines: List<Any>?,
    val dairyFree: Boolean?,
    val diets: List<Any>?,
    val dishTypes: List<String>?,
    val extendedIngredients: List<ExtendedIngredient>?,
    val gaps: String?,
    val glutenFree: Boolean?,
    val healthScore: Double?,
    val id: Int,
    val image: String?,
    val imageType: String?,
    val instructions: String?,
    val ketogenic: Boolean?,
    val license: String?,
    val lowFodmap: Boolean?,
    val occasions: List<Any>?,
    val pricePerServing: Double?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val sourceName: String?,
    val sourceUrl: String?,
    val spoonacularScore: Double?,
    val spoonacularSourceUrl: String?,
    val summary: String?,
    val sustainable: Boolean?,
    val title: String?,
    val vegan: Boolean?,
    val vegetarian: Boolean?,
    val veryHealthy: Boolean?,
    val veryPopular: Boolean?,
    val weightWatcherSmartPoints: Int?,
    val whole30: Boolean?,
    val winePairing: WinePairing?
)

@JsonClass(generateAdapter = true)
data class Us(
    val amount: Double?,
    val unitLong: String?,
    val unitShort: String?
)

@JsonClass(generateAdapter = true)
data class WinePairing(
    val pairedWines: List<String>?,
    val pairingText: String?,
    val productMatches: List<ProductMatche>?
)