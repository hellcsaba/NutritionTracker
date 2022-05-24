package hu.bme.aut.nutritiontracker.util

sealed class NetworkResult<out T>
data class NetworkSuccess<out T>(val value: T): NetworkResult<T>()
data class NetworkError(val errorMessage: Exception): NetworkResult<Nothing>()