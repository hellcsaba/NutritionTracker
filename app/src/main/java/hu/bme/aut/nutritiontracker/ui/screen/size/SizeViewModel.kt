package hu.bme.aut.nutritiontracker.ui.screen.size

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.nutritiontracker.data.Day
import hu.bme.aut.nutritiontracker.data.Measurement
import hu.bme.aut.nutritiontracker.data.User
import hu.bme.aut.nutritiontracker.model.FirestoreDatabaseRepository
import hu.bme.aut.nutritiontracker.util.NetworkError
import hu.bme.aut.nutritiontracker.util.NetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class SizeViewModel:ViewModel() {
    val firestoreRepository = FirestoreDatabaseRepository()
    private val _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User> = _userProfile

    private val _allMeasurements = MutableLiveData<List<Measurement>>()
    val allMeasurements: LiveData<List<Measurement>> = _allMeasurements

    private val _selectedDay = MutableLiveData<List<Day>>()
    val selectedDay: LiveData<List<Day>> = _selectedDay

    private val _measurement = MutableLiveData<List<Measurement>>()
    val measurement: LiveData<List<Measurement>> = _measurement


    init {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = firestoreRepository.getUser()){
                is NetworkSuccess -> _userProfile.postValue(response.value as User)
                is NetworkError -> Log.d("SizeViewModel", response.errorMessage.toString())
            }

            when(val response = firestoreRepository.getDay()){
                is NetworkSuccess -> {_selectedDay.postValue(response.value as List<Day>)
                Log.d("SizeViewModelDay", response.value.toString())}
                is NetworkError -> Log.d("SizeViewModelDay", response.errorMessage.toString())
            }

            selectedDay.value?.let {
                when (val response = firestoreRepository.getMeasurements(it[1])) {
                    is NetworkSuccess -> {
                        _measurement.postValue(response.value as List<Measurement>)
                        Log.d("SizeViewModelMeasurement", response.value.toString())
                    }
                    is NetworkError -> Log.d(
                        "SizeViewModelMeasurement",
                        response.errorMessage.toString()
                    )
                }


                firestoreRepository.getMeasurementsFlow(it[1]).collect{
                    _allMeasurements.postValue(it)
                }

            }
        }
    }

    fun addMeasurement(name: String, currentSize: Double?, previousSize: Double?){
        val measurement = Measurement(name = name, currentSize = currentSize, previousSize = previousSize)
        firestoreRepository.addMeasurement(day = selectedDay.value?.get(1)!!,measurement = measurement)
    }

    fun updateMeasurement(name: String, currentSize: Double?, previousSize: Double?){
        val measurement = Measurement(name = name, currentSize = currentSize, previousSize = previousSize)
        firestoreRepository.addMeasurement(day = selectedDay.value?.get(1)!!,measurement = measurement)
    }

    fun deleteMeasurement(id: String){
        firestoreRepository.deleteMeasurement(id)
    }

    fun addDay(){
        firestoreRepository.addDayToCollection()
    }

    fun getAllMeasurements(day: Day){
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = firestoreRepository.getAllMeasurements(day)){
                is NetworkSuccess -> _allMeasurements.postValue(response.value as List<Measurement>)
                is NetworkError -> Log.d("SizeViewModel", response.errorMessage.toString())
            }
        }
    }

    fun collectMeasurementData(day: Day){
        viewModelScope.launch {
            firestoreRepository.getMeasurementsFlow(day).collect{
                _allMeasurements.postValue(it)
            }
        }
    }


    val getAllData: MutableList<Measurement>? = mutableListOf(
        Measurement("Weight",null, previousSize = 70.0),
        Measurement("Bodyfat",null, 16.0),
        Measurement("Chest",null, 120.0),
        Measurement("Arm (right)", null, 40.0),
        Measurement("Arm (left)",null, 40.0),
        Measurement("Waist",null, 40.0),
        Measurement("Thigh (right)", null,61.2),
        Measurement("Thigh (left)", null,60.7)
    )


}