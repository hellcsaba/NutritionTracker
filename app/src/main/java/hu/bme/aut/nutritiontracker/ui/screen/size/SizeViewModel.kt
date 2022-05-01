package hu.bme.aut.nutritiontracker.ui.screen.size

import androidx.lifecycle.ViewModel
import hu.bme.aut.nutritiontracker.data.Measurement
import java.util.*

class SizeViewModel:ViewModel() {
    val getAllData: MutableList<Measurement>? = mutableListOf(
        Measurement("Weight",null, previousSize = 70.0),
        Measurement("Bodyfat",null, 16.0),
        Measurement("Chest",null, 120.0),
        Measurement("Arm (right)", null, 40.0),
        Measurement("Arm (left)",null, 40.0),
        Measurement("Waist",null, 40.0),
        Measurement("Thigh (right)", null,61.2),
        Measurement("Thigh (left)", null,60.7)
    )
}