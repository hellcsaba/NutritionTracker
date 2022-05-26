package hu.bme.aut.nutritiontracker.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    const val SpoonacularServer = "https://api.spoonacular.com"
    const val EdamamServer = "https://api.edamam.com"
    lateinit var retrofit: Retrofit.Builder

    fun setEdamamRetrofitClient(){
        retrofit =
            Retrofit.Builder()
                .baseUrl(EdamamServer)
                .addConverterFactory(MoshiConverterFactory.create())
    }

    fun setSpoonacularRetrofitClient(){
        retrofit =
            Retrofit.Builder()
                .baseUrl(SpoonacularServer)
                .addConverterFactory(MoshiConverterFactory.create())
    }

    val recipeApiInterface: RecipeAPI by lazy {
        retrofit
            .build()
            .create(RecipeAPI::class.java)
    }

    val foodApiInterface: FoodAPI by lazy {
        retrofit
            .build()
            .create(FoodAPI::class.java)
    }
}