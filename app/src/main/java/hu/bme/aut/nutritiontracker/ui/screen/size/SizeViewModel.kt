package hu.bme.aut.nutritiontracker.ui.screen.size

import android.util.Log
import androidx.lifecycle.*
import hu.bme.aut.nutritiontracker.data.Day
import hu.bme.aut.nutritiontracker.data.Measurement
import hu.bme.aut.nutritiontracker.data.User
import hu.bme.aut.nutritiontracker.model.FirestoreDatabaseRepository
import hu.bme.aut.nutritiontracker.ui.screen.diary.DiaryViewModel
import hu.bme.aut.nutritiontracker.util.NetworkError
import hu.bme.aut.nutritiontracker.util.NetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class SizeViewModel:ViewModel() {
    val firestoreRepository = FirestoreDatabaseRepository()
    private val _userProfile = MutableLiveData<User>()

    private val _allMeasurements = MutableLiveData<List<Measurement>>()
    val allMeasurements: LiveData<List<Measurement>> = _allMeasurements

    private val _selectedDay = MutableLiveData<List<Day>>()
    val selectedDay: LiveData<List<Day>> = _selectedDay

    private val _day = MutableLiveData<Day>()
    val day: LiveData<Day> = _day

    private lateinit var owner: LifecycleOwner
    fun attach(lifeCycleOwner: LifecycleOwner){
        owner = lifeCycleOwner
    }

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

        }


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
                        Log.d(DiaryViewModel.TAG, "DayFlow")
                        _day.postValue(day)
                    }
                }
            }
        }
    }

    fun getMeasurementsFlow(){
        day.observe(owner,
            object: Observer<Day> {
                override fun onChanged(res: Day) {
                    measurementsFlow()
                }
            })
    }

    private fun measurementsFlow(){
        selectedDay.value?.let {
            if(it.isNotEmpty()) {
                val res = firestoreRepository.getMeasurementsFlow(it[0])
                viewModelScope.launch(Dispatchers.IO) {
                    res.collect { list ->
                        _allMeasurements.postValue(list)
                    }
                }
            }
        }
    }

    fun addMeasurement(name: String, currentSize: Double?, previousSize: Double?){
        val measurement = Measurement(name = name, currentSize = currentSize, previousSize = previousSize)
        firestoreRepository.addMeasurement(day = selectedDay.value?.get(0)!!,measurement = measurement)
    }

    fun updateMeasurement(measurement: Measurement){
        firestoreRepository.updateMeasurement(day = selectedDay.value?.get(0)!!,measurement = measurement)
    }

    fun onMeasurementItemChanged(size: String, measureList: MutableList<Measurement>, measurement: Measurement) {
        var idx = -1
        if (size != "") {
            measureList.forEach { measure ->
                if (measure.name.equals(measurement.name))
                    idx = measureList.indexOf(measure)
            }
            val measure = Measurement(
                id = measurement.id,
                name = measurement.name,
                currentSize = size.toDouble(),
                previousSize = measurement.currentSize
            )
            measureList[idx] = measure
        }
    }

}