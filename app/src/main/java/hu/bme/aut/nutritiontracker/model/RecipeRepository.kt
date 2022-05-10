package hu.bme.aut.nutritiontracker.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.nutritiontracker.data.RecipeResult
import hu.bme.aut.nutritiontracker.datasource.RecipeNetworkDataSource

class RecipeRepository {
    fun getRecipes(name: String?): MutableLiveData<RecipeResult> {
        return RecipeNetworkDataSource.getRecipes(name)
    }
}