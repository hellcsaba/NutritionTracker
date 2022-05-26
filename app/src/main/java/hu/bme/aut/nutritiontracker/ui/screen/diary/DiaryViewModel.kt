package hu.bme.aut.nutritiontracker.ui.screen.diary

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.nutritiontracker.data.*
import hu.bme.aut.nutritiontracker.model.DiaryRepository
import hu.bme.aut.nutritiontracker.model.FirestoreDatabaseRepository
import hu.bme.aut.nutritiontracker.ui.screen.searchwidget.SearchWidgetState
import hu.bme.aut.nutritiontracker.util.NetworkError
import hu.bme.aut.nutritiontracker.util.NetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel : ViewModel() {
    private val firestoreRepository = FirestoreDatabaseRepository()
    private val diaryRepository = DiaryRepository()
    private val _searchedFoodResult = MutableLiveData<FoodResult>()
    val searchedFoodResult: LiveData<FoodResult> = _searchedFoodResult
    val _consumedFoodList = MutableLiveData<List<ConsumedFood>>()
    val consumedFoodList: LiveData<List<ConsumedFood>> = _consumedFoodList
    var selectedFood = Food()
    private val _selectedDay = MutableLiveData<List<Day>>()
    val selectedDay: LiveData<List<Day>> = _selectedDay
    private val _day = MutableLiveData<Day>()
    val day: LiveData<Day> = _day
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    private val _consumedKcal = MutableLiveData<Int>()
    val consumedKcal: LiveData<Int> = _consumedKcal
    private val _consumedMacros = MutableLiveData<MacroNutrition>()
    val consumedMacros: LiveData<MacroNutrition> = _consumedMacros

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private lateinit var owner: LifecycleOwner
    fun attach(lifeCycleOwner: LifecycleOwner){
        owner = lifeCycleOwner
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = firestoreRepository.getUser()){
                is NetworkSuccess -> {_user.postValue(response.value as User)
                    Log.d("UserDiary", response.value.toString())}
                is NetworkError -> Log.d("UserDiary" + TAG, response.errorMessage.toString())
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = firestoreRepository.getDay()) {
                is NetworkSuccess -> {
                    _selectedDay.postValue(response.value as List<Day>)
                    Log.d(TAG, response.value.toString())
                }
                is NetworkError -> Log.d(TAG, response.errorMessage.toString())
            }
        }

    }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun getDayFlow(){
        selectedDay.observe(owner,
            object: Observer<List<Day>> {
                override fun onChanged(res: List<Day>) {
                    dayFlow()
                }
            })
    }

    private fun dayFlow(){
        selectedDay.value?.let {
            if(it.isNotEmpty()) {
                val res = firestoreRepository.getDayFlow(it[0])
                viewModelScope.launch(Dispatchers.IO) {
                    res.collect { day ->
                        Log.d(TAG, "DayFlow")
                        _day.postValue(day)
                    }
                }
            }
        }
    }

    fun getConsumedFoodFlow(){
        day.observe(owner,
            object: Observer<Day> {
                override fun onChanged(res: Day) {
                    ConsumedFoodFlow()
                }
            })
    }

    private fun ConsumedFoodFlow(){
        day.value?.let{
            val res = firestoreRepository.getConsumedFoodFlow(day.value!!)
            viewModelScope.launch(Dispatchers.IO) {
                res.collect{ list ->
                    _consumedFoodList.postValue(list)
                }
            }
        }
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

    fun addConsumedFood(amount: String){
        val scale = amount.toInt() / 100
        val countedFood = ConsumedFood(
            name = selectedFood.label!!,
            amount = amount.toInt(),
            kcal = (selectedFood.nutrients!!.ENERC_KCAL!! * scale).toInt(),
            carb = (selectedFood.nutrients!!.CHOCDF!! * scale).toInt(),
            protein = (selectedFood.nutrients!!.PROCNT!! * scale).toInt(),
            fat = (selectedFood.nutrients!!.FAT!! * scale).toInt(),
            image = selectedFood.image
        )
        firestoreRepository.addConsumedFood(day = selectedDay.value?.get(0)!!, consumedFood = countedFood)
    }

    fun updateWaterConsumption(water: Double){
        firestoreRepository.updateWaterItem(day = day.value!!, water)
    }

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = firestoreRepository.getUser()){
                is NetworkSuccess -> {_user.postValue(response.value as User)
                    Log.d(TAG, "User" + response.value.toString())}
                is NetworkError -> Log.d(TAG, response.errorMessage.toString())
            }
        }
    }

    fun calculateConsumedMacrosAndKcal(){
        var kcal = 0
        var protein = 0
        var carb = 0
        var fat = 0
        consumedFoodList.value?.forEach {
            kcal += it.kcal!!
            protein += it.protein!!
            carb += it.carb!!
            fat += it.fat!!
        }
        _consumedKcal.value = kcal
        _consumedMacros.value = MacroNutrition(protein = protein, carb = carb, fat = fat)
    }

    companion object{
        const val TAG = "DiaryViewModel"
    }
}