package hu.bme.aut.nutritiontracker.ui.screen.recipe

import androidx.compose.runtime.*
import androidx.lifecycle.*
import hu.bme.aut.nutritiontracker.data.RecipeDetailResult
import hu.bme.aut.nutritiontracker.data.RecipeListResult
import hu.bme.aut.nutritiontracker.model.RecipeRepository
import hu.bme.aut.nutritiontracker.ui.screen.searchwidget.SearchWidgetState

class RecipeViewModel: ViewModel() {
    private var recipeRepository = RecipeRepository()
    lateinit var recipesList: MutableLiveData<RecipeListResult>
    var recipeDetail: MutableLiveData<RecipeDetailResult> = MutableLiveData()

    private lateinit var owner: LifecycleOwner
    fun attach(lifeCycleOwner: LifecycleOwner){
        owner = lifeCycleOwner
    }

    fun getRecipes(name: String?): LiveData<RecipeListResult>{
        recipesList = recipeRepository.getRecipesList(name)
        return recipesList
    }

    fun getRecipesList(name: String?){
        recipeRepository.getRecipesList(name).observe(owner,
            object: Observer<RecipeListResult> {
                override fun onChanged(res: RecipeListResult?) {
                    recipesList.postValue(res)
                }

            }
        )
    }

    fun getRecipe(id: Int): LiveData<RecipeDetailResult>{
        recipeDetail = recipeRepository.getRecipeDetail(id)
        return recipeDetail
    }

    fun getRecipeDetail(id: Int){
        recipeRepository.getRecipeDetail(id).observe(owner,
            object: Observer<RecipeDetailResult> {
                override fun onChanged(res: RecipeDetailResult?) {
                    recipeDetail.postValue(res)
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