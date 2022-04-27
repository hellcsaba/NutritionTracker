package hu.bme.aut.nutritiontracker.ui.screen.size

import androidx.lifecycle.ViewModel
import hu.bme.aut.nutritiontracker.data.Measurement
import java.util.*

class SizeViewModel:ViewModel() {
    val getAllData: MutableList<Measurement> = mutableListOf(
        Measurement("Weight", 70.0, Calendar.getInstance()),
        Measurement("Bodyfat", 16.0, Calendar.getInstance()),
        Measurement("Chest", 120.0, Calendar.getInstance()),
        Measurement("Arm (right)", 40.0, Calendar.getInstance()),
        Measurement("Arm (left)", 40.0, Calendar.getInstance()),
        Measurement("Waist", 40.0, Calendar.getInstance()),
        Measurement("Thigh (right)", 61.2, Calendar.getInstance()),
        Measurement("Thigh (left)", 60.7, Calendar.getInstance())
    )
}