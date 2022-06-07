package hu.bme.aut.nutritiontracker.ui.screen.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.nutritiontracker.data.BodyComposition
import hu.bme.aut.nutritiontracker.data.MacroNutrition
import hu.bme.aut.nutritiontracker.data.User
import hu.bme.aut.nutritiontracker.model.FirestoreDatabaseRepository
import hu.bme.aut.nutritiontracker.ui.screen.diary.DiaryViewModel
import hu.bme.aut.nutritiontracker.util.NetworkError
import hu.bme.aut.nutritiontracker.util.NetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.util.*

class ProfileViewModel: ViewModel() {
    private val firestoreRepository = FirestoreDatabaseRepository()
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        getUser()
    }

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = firestoreRepository.getUser()) {
                is NetworkSuccess -> {
                    _user.postValue(response.value as User)
                    Log.d(TAG, response.value.toString())
                }
                is NetworkError -> Log.d(TAG, response.errorMessage.toString())
            }
        }
    }

    private fun updateMacroTotal(protein: Int, carb: Int, fat:Int){
        val macro = MacroNutrition(
            protein = protein,
            carb = carb,
            fat = fat
        )
        firestoreRepository.updateMacroTotal(macro)

    }

    fun validateInput(weight: String, height: String, age: String, target: String, gender: String): Boolean{
        if(weight.isEmpty() || height.isEmpty() || age.isEmpty()
            || target.isEmpty() || gender.isEmpty())
                return false
        return true
    }

    fun calculateKcalLimit(weight: String, height: String, age: String, target: String, gender: String) {
        var kcalLimit = 0.0
        if (gender.equals("Male"))
            kcalLimit =
                1.2 * (66.46 + (13.7 * weight.toDouble()) + (5 * height.toInt()) - (6.8 * age.toInt())) - (1100 * target.toDouble())
        else
            kcalLimit =
                (655.1 + (9.6 * weight.toDouble()) + (1.8 * height.toInt()) - (4.7 * age.toInt())) * 1.2 - (1100 * target.toDouble())
        val body = BodyComposition(gender = gender, weight = weight.toInt(), height = height.toInt(), age = age.toInt())

        val fat = ((kcalLimit * 0.25) / 9).toInt()
        val carb = ((kcalLimit * 0.35) / 4).toInt()
        val protein = ((kcalLimit * 0.4) /4).toInt()
        updateMacroTotal(protein = protein, fat = fat, carb = carb)
        firestoreRepository.updateBodyCompositionAndKcalLimit(kcalLimit.toInt(), body)
    }

    companion object{
        const val TAG = "ProfileViewModel"
    }
}