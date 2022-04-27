package hu.bme.aut.nutritiontracker.ui.screen.size

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.nutritiontracker.data.Measurement
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun MeasurementItem(measurement: Measurement) {
    var size by remember {mutableStateOf("")}
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(6.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Box(
            modifier = Modifier.weight(5.0f)
        ) {
            Text(
                text = measurement.name,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .width(50.dp)
                .clip(RoundedCornerShape(6.dp))
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = measurement.size.toString(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(color = Color.Gray)
                    .align(Alignment.Center)
                    .padding(start = 10.dp, bottom = 5.dp, top = 5.dp, end = 5.dp),
                maxLines = 1
            )
        }
        Box(
            modifier = Modifier
                .width(50.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(color = Color.White)
        ) {
            BasicTextField(
                value = size,
                onValueChange = { size = it },
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .align(Alignment.Center)
                    .padding(5.dp),
                textStyle = TextStyle(fontSize = 20.sp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}


@Composable
@Preview
fun MeasurementItemPreview() {
    MeasurementItem(
        measurement = Measurement(
            name = "Weight",
            size = 70.0,
            date = Calendar.getInstance()
        )
    )
}