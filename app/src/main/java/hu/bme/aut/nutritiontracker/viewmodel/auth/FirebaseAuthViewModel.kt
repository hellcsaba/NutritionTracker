package hu.bme.aut.nutritiontracker.viewmodel.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import hu.bme.aut.nutritiontracker.model.FirebaseAuthRepository


//class FirebaseAuthViewModel(
//    application: Application
//): AndroidViewModel(application){
//    private val authRepository: FirebaseAuthRepository
//    private var userLiveData: MutableLiveData<FirebaseUser?>? = null
//
//   init{
//        authRepository = FirebaseAuthRepository()
//        userLiveData = authRepository.getUserLiveData()
//    }
//
//    fun login(email: String, password: String) {
//        authRepository.signIn(email, password)
//    }
//
//    fun signup(email: String, password: String) {
//        authRepository.createAccount(email, password)
//    }
//
//    fun getUserLiveData(): MutableLiveData<FirebaseUser?>? {
//        return userLiveData
//    }
//}