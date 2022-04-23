package hu.bme.aut.nutritiontracker.ui.screen.authentication


import androidx.lifecycle.ViewModel
import hu.bme.aut.nutritiontracker.model.FirebaseAuthRepository

class AuthenticationViewModel: ViewModel(){

    private val firebaseAuthRepository = FirebaseAuthRepository()

    fun signIn(email: String, password: String): Boolean {
        return firebaseAuthRepository.signIn(email = email, password = password)
    }

    fun signUp(email: String, password: String, confirmPassword: String): Boolean {
        return firebaseAuthRepository.signUp(email = email, password = password, confirmPassword = confirmPassword)
    }

    fun logOut() {
        return firebaseAuthRepository.logOut()
    }

}