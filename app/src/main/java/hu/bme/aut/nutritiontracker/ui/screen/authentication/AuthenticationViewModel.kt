package hu.bme.aut.nutritiontracker.ui.screen.authentication


import androidx.lifecycle.*
import hu.bme.aut.nutritiontracker.data.RecipeListResult
import hu.bme.aut.nutritiontracker.model.FirebaseAuthRepository
import hu.bme.aut.nutritiontracker.model.FirestoreDatabaseRepository
import javax.security.auth.callback.Callback

class AuthenticationViewModel: ViewModel(){

    //private val FirebaseAuthRepository = FirebaseAuthRepository()
    private val firestoreDatabaseRepository = FirestoreDatabaseRepository()
    private val _loggedIn : MutableLiveData<Boolean> = FirebaseAuthRepository.loggedInLiveData
    val loggedIn : LiveData<Boolean> = _loggedIn

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
                override fun onChanged(res: Boolean?) {
                    _loggedIn.value = res
                    onChanged()
                }

            }
        )
    }
}