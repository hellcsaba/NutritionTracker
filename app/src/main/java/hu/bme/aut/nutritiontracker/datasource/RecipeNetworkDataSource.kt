package hu.bme.aut.nutritiontracker.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.nutritiontracker.BuildConfig
import hu.bme.aut.nutritiontracker.data.RecipeResult
import hu.bme.aut.nutritiontracker.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RecipeNetworkDataSource {
    fun getRecipes(name: String?): MutableLiveData<RecipeResult>{
        val call = RetrofitClient.apiInterface.getFood("1cc3f4b2574241cf961033b15b12f621", name)

        val recipeResultData = MutableLiveData<RecipeResult>()

        call.enqueue(object: Callback<RecipeResult>{
            override fun onResponse(call: Call<RecipeResult>, response: Response<RecipeResult>){
                Log.d("DEBUG onResponse", response.body().toString())
                recipeResultData.value = response.body()
            }

            override fun onFailure(call: Call<RecipeResult>, t: Throwable) {
                Log.d("DEBUG onFailure", t.message.toString())
            }
        })

        return recipeResultData
    }
}