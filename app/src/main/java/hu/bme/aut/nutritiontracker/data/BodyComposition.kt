package hu.bme.aut.nutritiontracker.data

import java.sql.Timestamp
import java.util.*

data class BodyComposition(
    var gender: String = "",
    var weight: Int = 0,
    var height: Int = 0,
    var birthday: Timestamp = Timestamp(Calendar.getInstance().time.time)
)