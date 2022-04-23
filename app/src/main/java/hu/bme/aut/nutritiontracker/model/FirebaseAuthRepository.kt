package hu.bme.aut.nutritiontracker.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class FirebaseAuthRepository
{
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var userLiveData: MutableLiveData<FirebaseUser?>? = null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null

    init {
        userLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()
        if (auth.currentUser != null) {
            userLiveData!!.postValue(auth.currentUser)
            loggedOutLiveData!!.postValue(false)
        }
    }

    fun signIn(email: String, password: String): Boolean{
        if(email.isEmpty() || password.isEmpty()){
            Log.d("SignIn Validate", "Validation failed")
            return false
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("EmailPassword", "signInWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("EmailPassword", "signInWithEmail:failure", task.exception)
                }
            }
        return true
    }

    fun signUp(email: String, password: String, confirmPassword: String):Boolean{
        if(validateSignUpCredentials(email = email, password = password, confirmPassword = confirmPassword)) {
            createAccount(email = email, password = password)
            return true
        }
        return false
    }

    private fun validateSignUpCredentials(email: String, password: String, confirmPassword: String):Boolean{
        if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
            || !(password.equals(confirmPassword))) {
            Log.d("SignUp Validate", "Validation failed")
            return false
        }
        return true
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("EmailPassword", "createUserWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("EmailPassword", "createUserWithEmail:failure", task.exception)
                }
            }.addOnFailureListener {
                Log.d("firebase", it.message.toString())
            }
    }

    fun logOut() {
        auth!!.signOut()
        loggedOutLiveData!!.postValue(true)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser?>? {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
        return loggedOutLiveData
    }

}