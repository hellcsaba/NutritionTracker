package hu.bme.aut.nutritiontracker.datasource


import hu.bme.aut.nutritiontracker.network.RetrofitClient
import hu.bme.aut.nutritiontracker.util.NetworkError
import hu.bme.aut.nutritiontracker.util.NetworkResult
import hu.bme.aut.nutritiontracker.util.NetworkSuccess
import java.lang.Exception

const val APP_ID = "504f4457"
const val APP_KEY = "63b6ab749634fb8a793a2b18d68211b2"

object FoodNetworkDataSource {
    suspend fun getFoodBySearch(search: String): NetworkResult<Any>{
        try {
            RetrofitClient.setEdamamRetrofitClient()
            val response = RetrofitClient.foodApiInterface.getFoodBySearch(app_id = APP_ID, app_key = APP_KEY, ingr = search )
            response.body().let {
                return NetworkSuccess(it!!)
            }
            return NetworkError(Exception("No food data"))
        } catch (e: Exception) {
            return NetworkError(e)
        }
    }
}