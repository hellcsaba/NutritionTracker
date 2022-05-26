package hu.bme.aut.nutritiontracker.ui.screen.diary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import hu.bme.aut.nutritiontracker.data.Food

@Composable
fun AddConsumedFoodDialog(
    onConfirmClicked: () -> Unit,
    onDismiss: () -> Unit,
    measure: String,
    onMeasureChanged: (String) -> Unit,
    food: Food
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add consumed food",
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = food.label!!,
                        style = MaterialTheme.typography.body2,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )

                    KeyValueRow(
                        property = "Kcal: ",
                        value = food.nutrients!!.ENERC_KCAL.toString()
                    )

                    KeyValueRow(
                        property = "Protein: ",
                        value = food.nutrients.PROCNT.toString()
                    )

                    KeyValueRow(
                        property = "Carbs: ",
                        value = food.nutrients.CHOCDF.toString()
                    )

                    KeyValueRow(
                        property = "Fat: ",
                        value = food.nutrients.FAT.toString()
                    )


                    OutlinedTextField(
                        value = measure,
                        onValueChange = {
                            onMeasureChanged(it)
                        },
                        label = {
                            Text(text = "Amount (g)")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onConfirmClicked
                            }
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // BUTTONS
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = onConfirmClicked) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}

@Composable
fun KeyValueRow(property: String, value: String){
    Row(
        modifier = Modifier.padding(horizontal = 6.dp)
    ){
        Text(
            text = property,
            style = MaterialTheme.typography.body2,
            fontSize = 18.sp
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            fontSize = 18.sp
        )
    }
}