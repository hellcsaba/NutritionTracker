package hu.bme.aut.nutritiontracker.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.nutritiontracker.data.*
import hu.bme.aut.nutritiontracker.util.NetworkError
import hu.bme.aut.nutritiontracker.util.NetworkResult
import hu.bme.aut.nutritiontracker.util.NetworkSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

const val TAG = "FirestoreDatabaseRepo"

class FirestoreDatabaseRepository {
    private val db = Firebase.firestore
    private val user = FirebaseAuth.getInstance().currentUser


    suspend fun getUser(): NetworkResult<Any> {
        try {
            val response = db.collection("users").document(user?.uid!!).get().await().toObject(User::class.java)
            response?.let{
                return NetworkSuccess(it)
            }
            return NetworkError(Exception("Get user data error"))
        } catch (exception: Exception) {
           return NetworkError(exception)
        }
    }

    suspend fun getDay(day: Date = Date()): NetworkResult<Any> {
        return try {
            val start = Date(day.time)
            val end = Date(day.time)
            start.hours = 0
            start.minutes = 0
            start.seconds = 0
            end.hours = 23
            end.minutes = 59
            end.seconds = 59
            val response = db.collection("users").document(user?.uid!!).collection("days")
                .whereGreaterThan("day", start)
                .whereLessThanOrEqualTo("day", end)
                .get().await()
                .toObjects(Day::class.java)
            NetworkSuccess(response)
        } catch (exception: Exception) {
            NetworkError(exception)
        }
    }

    suspend fun getMeasurements(day: Day): NetworkResult<Any> {
        try {
//            val start = Date(day.time)
//            val end = Date(day.time)
//            start.hours = 0
//            start.minutes = 0
//            start.seconds = 0
//            end.hours = 23
//            end.minutes = 59
//            end.seconds = 59
//            val responseDay = db.collection("users").document(user?.uid!!).collection("days")
//                .whereGreaterThan("day", start)
//                .whereLessThanOrEqualTo("day", end)
//                .get().await().documents.

            val response = db.collection("users").document(user?.uid!!).collection("days").document(day.parentId!!).collection("measurements")
                .get().await().toObjects(Measurement::class.java)
            response?.let{
                return NetworkSuccess(it)
            }
            return NetworkError(Exception("Get user data error"))
        } catch (exception: Exception) {
            return NetworkError(exception)
        }
    }


    fun addMeasurement(day: Day, measurement: Measurement){
        db.collection("users").document(user?.uid!!).collection("days").document(day.parentId!!).collection("measurements").add(measurement)
            .addOnCompleteListener{
                Log.d("FirestoreRepo", "measurement added")
            }
    }

    fun updateMeasurement(){
        
    }

    fun deleteMeasurement(id: String){
        db.collection("users").document(user?.uid!!).collection("measurements").document(id)
            .delete()
            .addOnSuccessListener {  Log.d(TAG, "DocumentSnapshot successfully deleted!")  }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    fun addDayToCollection(){
        db.collection("users").document(FirebaseAuthRepository.auth.currentUser?.uid!!).collection("days").add(Day())
            .addOnSuccessListener {  Log.d(TAG, "Day was added successfully!")  }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding day document", e) }
    }


    fun createUser(user: User, userID: String){
        db.collection("users").document(userID).set(user)
    }

    suspend fun getAllMeasurements(day: Day): NetworkResult<Any> {
        try {
            val response = db.collection("users")
                .document(FirebaseAuthRepository.auth.currentUser?.uid!!).collection("days").document(day.parentId!!).collection("measurements")
                .get().await().documents.mapNotNull {
                        snapShot -> snapShot.toObject(Measurement::class.java)
            }
            response?.let{
                return NetworkSuccess(it)
            }
        } catch (exception: Exception) {
            return NetworkError(exception)
        }
    }

    @ExperimentalCoroutinesApi
    fun getMeasurementsFlow(day: Day): Flow<List<Measurement>> {
        return db
            .collection("users")
            .document(FirebaseAuthRepository.auth.currentUser?.uid!!).collection("days").document(day.parentId!!).collection("measurements")
            .getDataFlow { querySnapshot ->
                querySnapshot?.documents?.map {
                    getMeasurementItemFromSnapshot(it)
                } ?: listOf()
            }
    }

    // Parses the document snapshot to the desired object
    private fun getMeasurementItemFromSnapshot(documentSnapshot: DocumentSnapshot) : Measurement {
        return documentSnapshot.toObject(Measurement::class.java)!!
    }

    suspend fun getDay(userID: String): NetworkResult<Any> {
        try {
            val response = db.collection("users").document(userID).get().await().toObject(User::class.java)
            response?.let{
                return NetworkSuccess(it)
            }
            return NetworkError(Exception("Get user data error"))
        } catch (exception: Exception) {
            return NetworkError(exception)
        }
    }


}


@ExperimentalCoroutinesApi
fun CollectionReference.getQuerySnapshotFlow(): Flow<QuerySnapshot?> {
    return callbackFlow {
        val listenerRegistration =
            addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    cancel(
                        message = "error fetching collection data at path - $path",
                        cause = firebaseFirestoreException
                    )
                    return@addSnapshotListener
                }
                trySend(querySnapshot).isSuccess
            }
        awaitClose {
            Log.d(TAG,"cancelling the listener on collection at path - $path")
            listenerRegistration.remove()
        }
    }
}


@ExperimentalCoroutinesApi
fun <T> CollectionReference.getDataFlow(mapper: (QuerySnapshot?) -> T): Flow<T> {
    return getQuerySnapshotFlow()
        .map {
            return@map mapper(it)
        }
}