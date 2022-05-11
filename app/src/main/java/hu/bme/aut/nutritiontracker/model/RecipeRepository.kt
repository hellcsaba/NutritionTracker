package hu.bme.aut.nutritiontracker.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.nutritiontracker.data.RecipeDetailResult
import hu.bme.aut.nutritiontracker.data.RecipeListResult
import hu.bme.aut.nutritiontracker.datasource.RecipeNetworkDataSource

class RecipeRepository {
    fun getRecipesList(name: String?): MutableLiveData<RecipeListResult> {
        return RecipeNetworkDataSource.getRecipesList(name)
    }

    fun getRecipeDetail(id: Int): MutableLiveData<RecipeDetailResult>{
        return RecipeNetworkDataSource.getRecipeDetail(id)
    }
}