package hu.bme.aut.nutritiontracker.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
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
            val response = db.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid!!).get().await().toObject(User::class.java)
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

    suspend fun addDay(date: Date = Date(), day: Day = Day()):Boolean{
        var success = false
        val start = Date(date.time)
        val end = Date(date.time)
        start.hours = 0
        start.minutes = 0
        start.seconds = 0
        end.hours = 23
        end.minutes = 59
        end.seconds = 59
        //Check whether the day exists
        val response = db.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid!!).collection("days")
            .whereGreaterThan("day", start)
            .whereLessThanOrEqualTo("day", end)
            .get().await().documents.map{ documentSnapshot ->
                val res = documentSnapshot.toObject(Day::class.java)!!
                res.id = documentSnapshot.id
            }

        if(response.isEmpty())
            db.collection("users").document(FirebaseAuthRepository.auth.currentUser?.uid!!).collection("days").add(day)
                .addOnSuccessListener {
                    //set the id of the day
                    db.collection("users").document(FirebaseAuthRepository.auth.currentUser?.uid!!).collection("days").document(it.id).update("id", it.id)
                    success = true
                    Log.d(TAG, "Day was added successfully!")
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error adding day document", e) }
        success = true
        return success
    }

    fun addMeasurement(day: Day, measurement: Measurement){
        db.collection("users").document(user?.uid!!).collection("days").document(day.id!!).collection("measurements").add(measurement)
            .addOnCompleteListener{
                Log.d(TAG, "measurement added")
            }
    }

    fun updateMeasurement(day: Day, measurement: Measurement){
        db.collection("users").document(user?.uid!!).collection("days").document(day.id!!).collection("measurements").document(measurement.id!!).set(measurement)
    }

    fun updateMacroTotal(macro: MacroNutrition){
        db.collection("users").document(user?.uid!!).update("macroTotal", macro)
    }

    fun updateBodyCompositionAndKcalLimit(kcalLimit: Int, body: BodyComposition){
        db.collection("users").document(user?.uid!!).update("bodyComposition", body)
        db.collection("users").document(user?.uid!!).update("kcalLimit", kcalLimit)
    }

    @ExperimentalCoroutinesApi
    fun getMeasurementsFlow(day: Day): Flow<List<Measurement>> {
        return db
            .collection("users")
            .document(FirebaseAuthRepository.auth.currentUser?.uid!!).collection("days").document(day.id!!).collection("measurements")
            .getDataFlow { querySnapshot ->
                querySnapshot?.documents?.map {
                    getMeasurementItemFromSnapshot(it)
                } ?: listOf()
            }
    }

    // Parses the document snapshot to the desired object
    private fun getMeasurementItemFromSnapshot(documentSnapshot: DocumentSnapshot) : Measurement {
        val measurement = documentSnapshot.toObject(Measurement::class.java)!!
        measurement.id = documentSnapshot.id
        return measurement
    }

    fun addConsumedFood(day: Day, consumedFood: ConsumedFood){
        db.collection("users").document(FirebaseAuthRepository.auth.currentUser?.uid!!).collection("days").document(day.id!!).collection("consumedFoods").add(consumedFood)
            .addOnSuccessListener{
                db.collection("users").document(FirebaseAuthRepository.auth.currentUser?.uid!!)
                    .collection("days").document(day.id!!)
                    .collection("consumedFoods").document(it.id).update("id", it.id)
                Log.d(TAG, "Consumed food added")
            }
    }

    @ExperimentalCoroutinesApi
    fun getConsumedFoodFlow(day: Day): Flow<List<ConsumedFood>> {
        return db
            .collection("users")
            .document(FirebaseAuthRepository.auth.currentUser?.uid!!).collection("days").document(day.id!!).collection("consumedFoods")
            .getDataFlow { querySnapshot ->
                querySnapshot?.documents?.map {
                    getConsumedFoodItemFromSnapshot(it)
                } ?: listOf()
            }
    }

    private fun getConsumedFoodItemFromSnapshot(documentSnapshot: DocumentSnapshot) : ConsumedFood {
        val consumedFood = documentSnapshot.toObject(ConsumedFood::class.java)!!
        consumedFood.id = documentSnapshot.id
        return consumedFood
    }

    @ExperimentalCoroutinesApi
    fun getDayFlow(day: Day): Flow<Day> {
        return db
            .collection("users")
            .document(FirebaseAuthRepository.auth.currentUser?.uid!!).collection("days").document(day.id!!)
            .getDocumentSnapshotFlow().map { DocumentSnapshot ->
                        getDayFromSnapshot(DocumentSnapshot!!)
            }
    }

    private fun getDayFromSnapshot(documentSnapshot: DocumentSnapshot) : Day {
        val day = documentSnapshot.toObject(Day::class.java)!!
        Log.d("getDayFromSnapshot", documentSnapshot.id)
        day.id = documentSnapshot.id
        return day
    }

    fun updateWaterItem(day: Day, water: Double){
        db.collection("users").document(user?.uid!!).collection("days").document(day.id!!).update("water", water)
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

@ExperimentalCoroutinesApi
fun DocumentReference.getDocumentSnapshotFlow(): Flow<DocumentSnapshot?> {
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
