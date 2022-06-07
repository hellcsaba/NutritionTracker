package hu.bme.aut.nutritiontracker.ui.screen.authentication


import android.util.Log
import androidx.lifecycle.*
import hu.bme.aut.nutritiontracker.data.Day
import hu.bme.aut.nutritiontracker.data.RecipeListResult
import hu.bme.aut.nutritiontracker.data.User
import hu.bme.aut.nutritiontracker.model.FirebaseAuthRepository
import hu.bme.aut.nutritiontracker.model.FirestoreDatabaseRepository
import hu.bme.aut.nutritiontracker.util.NetworkError
import hu.bme.aut.nutritiontracker.util.NetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.security.auth.callback.Callback

class AuthenticationViewModel: ViewModel(){
    private val firestoreDatabaseRepository = FirestoreDatabaseRepository()
    private val _loggedIn : MutableLiveData<Boolean> = FirebaseAuthRepository.loggedInLiveData
    private val _selectedDay: MutableLiveData<List<Day>> = MutableLiveData<List<Day>>()
    val loggedIn : LiveData<Boolean> = _loggedIn

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = firestoreDatabaseRepository.getDay()) {
                is NetworkSuccess -> {
                    _selectedDay.postValue(response.value as List<Day>)
                    Log.d("AuthenticationViewModel", response.value.toString())
                }
                is NetworkError -> Log.d("AuthenticationViewModel", response.errorMessage.toString())
            }
        }
    }

    private lateinit var owner: LifecycleOwner
    fun attach(lifeCycleOwner: LifecycleOwner){
        owner = lifeCycleOwner
    }

    fun signUp(email: String, password: String, confirmPassword: String): Boolean {
        return FirebaseAuthRepository.signUp(email = email, password = password, confirmPassword = confirmPassword)
    }

    fun logOut() {
        return FirebaseAuthRepository.logOut()
    }

    fun signIn(email: String, password: String, onChanged: () -> Unit){
        FirebaseAuthRepository.signIn(email = email, password = password).observe(owner,
            object: Observer<Boolean> {
                override fun onChanged(res: Boolean) {
                    if(res)
                        viewModelScope.launch(Dispatchers.IO) {
                            firestoreDatabaseRepository.addDay()
                            _loggedIn.postValue(firestoreDatabaseRepository.addDay())
                        }
                    else{
                        _loggedIn.value = false
                    }
                    Log.d("SignIN", "onChangedCall")
                    onChanged()
                    //if(_selectedDay.value.isNullOrEmpty())

                }

            }
        )
    }
}