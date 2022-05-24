package hu.bme.aut.nutritiontracker.network

import hu.bme.aut.nutritiontracker.data.FoodResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodAPI {
    @GET("/api/food-database/v2/parser")
    fun getFoodBySearch(@Query("app_id") app_id: String,
                @Query("app_key") app_key: String,
                @Query("ingr") ingr: String): Response<FoodResult>
}