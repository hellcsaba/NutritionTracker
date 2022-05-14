package hu.bme.aut.nutritiontracker.network

import hu.bme.aut.nutritiontracker.data.RecipeDetailResult
import hu.bme.aut.nutritiontracker.data.RecipeListResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeAPI {
    @GET("/recipes/complexSearch")
    fun getRecipesList(@Query("apiKey") apiKey: String,
                @Query("query") query: String?): Call<RecipeListResult>

    @GET("/recipes/{id}/information")
    fun getRecipe(@Path("id") id: Int?,
                  @Query("apiKey") apiKey: String
    //              @Query("query") query: String? = "includeNutrition=false"
    ): Call<RecipeDetailResult>

}