package hu.bme.aut.nutritiontracker.model

import hu.bme.aut.nutritiontracker.datasource.FoodNetworkDataSource
import hu.bme.aut.nutritiontracker.util.NetworkResult

class DiaryRepository {
    suspend fun getFoodBySearch(search: String): NetworkResult<Any>{
        return FoodNetworkDataSource.getFoodBySearch(search)
    }
}