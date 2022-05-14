package hu.bme.aut.nutritiontracker.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.nutritiontracker.data.RecipeDetailResult
import hu.bme.aut.nutritiontracker.data.RecipeListResult
import hu.bme.aut.nutritiontracker.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val RECIPE_APIKEY = "1cc3f4b2574241cf961033b15b12f621"

object RecipeNetworkDataSource {
    fun getRecipesList(name: String?): MutableLiveData<RecipeListResult>{
        val call = RetrofitClient.apiInterface.getRecipesList(RECIPE_APIKEY, name)

        val RecipeListResultData = MutableLiveData<RecipeListResult>()

        call.enqueue(object: Callback<RecipeListResult>{
            override fun onResponse(call: Call<RecipeListResult>, response: Response<RecipeListResult>){
                Log.d("DEBUG onResponse", response.body().toString())
                RecipeListResultData.value = response.body()
            }

            override fun onFailure(call: Call<RecipeListResult>, t: Throwable) {
                Log.d("DEBUG onFailure", t.message.toString())
            }
        })

        return RecipeListResultData
    }

    fun getRecipeDetail(id: Int): MutableLiveData<RecipeDetailResult>{
        val call = RetrofitClient.apiInterface.getRecipe(id ,RECIPE_APIKEY)

        val RecipeDetailResultData = MutableLiveData<RecipeDetailResult>()

        call.enqueue(object: Callback<RecipeDetailResult>{
            override fun onResponse(call: Call<RecipeDetailResult>, response: Response<RecipeDetailResult>){
                Log.d("DEBUG onResponse", response.body().toString())
                RecipeDetailResultData.value = response.body()
            }

            override fun onFailure(call: Call<RecipeDetailResult>, t: Throwable) {
                Log.d("DEBUG onFailure", t.message.toString())
            }

        })

        return RecipeDetailResultData
    }
}