package hu.bme.aut.nutritiontracker.ui.screen.recipe

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.*
import hu.bme.aut.nutritiontracker.data.Recipe
import hu.bme.aut.nutritiontracker.data.RecipeResult
import hu.bme.aut.nutritiontracker.datasource.RecipeNetworkDataSource
import hu.bme.aut.nutritiontracker.model.RecipeRepository

class RecipeViewModel: ViewModel() {
    private var recipeRepository = RecipeRepository()
    lateinit var recipesList: MutableLiveData<RecipeResult>

    fun getRecipes(name: String?): LiveData<RecipeResult>{
        recipesList = recipeRepository.getRecipes(name)
        return recipesList
    }

    private lateinit var owner: LifecycleOwner
    fun attach(lifeCycleOwner: LifecycleOwner){
        owner = lifeCycleOwner
    }

    fun getRecipesList(name: String?){
        recipeRepository.getRecipes(name).observe(owner,
            object: Observer<RecipeResult> {
                override fun onChanged(res: RecipeResult?) {
                    recipesList.postValue(res)
                }

            }
        )
    }



    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }


}