package hu.bme.aut.nutritiontracker.network

import hu.bme.aut.nutritiontracker.data.RecipeResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeAPI {
    @GET("/recipes/complexSearch")
    fun getFood(@Query("apiKey") apiKey: String,
                @Query("query") query: String?): Call<RecipeResult>
}