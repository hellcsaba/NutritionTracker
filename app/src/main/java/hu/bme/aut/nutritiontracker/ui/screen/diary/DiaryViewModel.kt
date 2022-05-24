package hu.bme.aut.nutritiontracker.ui.screen.diary

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.nutritiontracker.data.Day
import hu.bme.aut.nutritiontracker.data.Food
import hu.bme.aut.nutritiontracker.data.FoodResult
import hu.bme.aut.nutritiontracker.model.DiaryRepository
import hu.bme.aut.nutritiontracker.ui.screen.searchwidget.SearchWidgetState
import hu.bme.aut.nutritiontracker.util.NetworkError
import hu.bme.aut.nutritiontracker.util.NetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel : ViewModel() {
    private val diaryRepository = DiaryRepository()
    private val _searchedFoodResult = MutableLiveData<FoodResult>()
    val searchedFoodResult: LiveData<FoodResult> = _searchedFoodResult

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

    fun getFoodBySearch(search: String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = diaryRepository.getFoodBySearch(search)){
                is NetworkSuccess -> {_searchedFoodResult.postValue(response.value as FoodResult)
                    Log.d(TAG, response.value.toString())}
                is NetworkError -> Log.d(TAG, response.errorMessage.toString())
            }
        }
    }

    companion object{
        const val TAG = "DiaryViewModel"
    }
}