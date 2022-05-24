package hu.bme.aut.nutritiontracker.model

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.nutritiontracker.data.RecipeListResult
import hu.bme.aut.nutritiontracker.data.User
import javax.security.auth.callback.Callback


object FirebaseAuthRepository
{
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var userLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    var loggedInLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        userLiveData = MutableLiveData()
        logOut()
        if (auth.currentUser != null) {
            userLiveData.postValue(auth.currentUser)
            loggedInLiveData.postValue(true)
        }
    }

    fun signIn(email: String, password: String): MutableLiveData<Boolean> {
        loggedInLiveData = MutableLiveData<Boolean>()
        if(email.isEmpty() || password.isEmpty()){
            Log.d("SignIn Validate", "Validation failed")
            loggedInLiveData.postValue(false)
        }

        if(!(email.isEmpty() || password.isEmpty())){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("EmailPassword", "signInWithEmail:success")
                        loggedInLiveData.postValue(true)
                        userLiveData.postValue(auth.currentUser)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("EmailPassword", "signInWithEmail:failure", task.exception)
                        loggedInLiveData.postValue(false)
                    }
                }
        }
        return loggedInLiveData
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
                    val user = User()
                    user.email =  auth.currentUser?.email!!
                    Firebase.firestore.collection("users").document(auth.currentUser?.uid!!).set(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("EmailPassword", "createUserWithEmail:failure", task.exception)
                }
            }.addOnFailureListener {
                Log.d("firebase", it.message.toString())
            }
    }

    fun logOut() {
        auth.signOut()
        loggedInLiveData.postValue(false)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser?>? {
        return userLiveData
    }


}