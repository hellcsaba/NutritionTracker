package hu.bme.aut.nutritiontracker.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.security.AccessController.getContext


object FirebaseAuthRepository
    //( application: Application )
{

    //private val application: Application
    private lateinit var auth: FirebaseAuth
    private var userLiveData: MutableLiveData<FirebaseUser?>? = null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null

    init {
        //this.application = application
        auth = FirebaseAuth.getInstance()
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
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("EmailPassword", "signInWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("EmailPassword", "signInWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    //updateUI(null)
                }
            }
        return true
        // [END sign_in_with_email]
    }

    fun signUp(email: String, password: String, confirmPassword: String){
        if(validateSignUpCredentials(email = email, password = password, confirmPassword = confirmPassword))
            createAccount(email = email, password = password)
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
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("EmailPassword", "createUserWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
//                    Toast.makeText(
//                       this@FirebaseAuthRepository,
//                        "Registration was successful",
//                        Toast.LENGTH_SHORT
//                    ).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("EmailPassword", "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        application.baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    //updateUI(null)
                }
            }.addOnFailureListener {
                Log.d("firebase", it.message.toString())
            }
        // [END create_user_with_email]
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

//    companion object {
//        private const val TAG = "EmailPassword"
//    }
}