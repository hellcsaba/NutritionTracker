package hu.bme.aut.nutritiontracker.ui.screen.size


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@Composable
fun AddMeasurementDialog(
    onConfirmClicked: () -> Unit,
    onDismiss: () -> Unit,
    name: String,
    onNameChanged: (String) -> Unit
) {
    //var name by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add measurement",
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = "You can add custom measurements",
                        style = MaterialTheme.typography.body2,
                        fontSize = 16.sp
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            onNameChanged(it)
                        },
                        Modifier.padding(top = 8.dp),
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
@Preview
fun ComposeEditModalPreview(){
    //CustomDialogScrollable({},{}, )
}
