package hu.bme.aut.nutritiontracker.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    const val MainServer = "https://api.spoonacular.com"

    val retrofitClient: Retrofit.Builder by lazy{
        Retrofit.Builder()
            .baseUrl(MainServer)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    val apiInterface: RecipeAPI by lazy {
        retrofitClient
            .build()
            .create(RecipeAPI::class.java)
    }
}