package hu.bme.aut.nutritiontracker.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.nutritiontracker.R
import hu.bme.aut.nutritiontracker.data.Measurement
import hu.bme.aut.nutritiontracker.showToast
import hu.bme.aut.nutritiontracker.ui.screen.size.AddMeasurementDialog
import hu.bme.aut.nutritiontracker.ui.screen.size.MeasurementItem
import hu.bme.aut.nutritiontracker.ui.screen.size.SizeViewModel
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SizeScreen(sizeViewModel: SizeViewModel) {
    val context = LocalContext.current
    var openDialog by rememberSaveable { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }

    Scaffold(
        topBar ={DefaultAppBar(
            onAddClicked = {
                openDialog = true
            },
            onSaveClicked = {}
        )},
        bottomBar = {}
    ){
        if(openDialog){
            AddMeasurementDialog(
                onConfirmClicked = {
                    showToast(context = context, "onConfirmClicked")
                    sizeViewModel.getAllData?.add(Measurement(name,null,null))
                    name = ""
                    openDialog = false
                },
                onDismiss = {
                    name = ""
                    openDialog = false },
                name = name,
                onNameChanged = {
                    name = it
                })
        }
        LazyColumn(
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            stickyHeader {
                Row(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    Box(
                        modifier = Modifier.weight(5.0f)
                    ) {
                        Text(
                            text = "Name",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier.width(50.dp)
                    ) {
                        Text(
                            text = "Prev.",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Box(
                        modifier = Modifier.width(50.dp)
                    ) {
                        Text(
                            text = "Curr.",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    }


                }
            }

            if(!sizeViewModel.getAllData.isNullOrEmpty()){
                items(items = sizeViewModel.getAllData) { measurement ->
                    MeasurementItem(measurement = measurement)
                }
            }
        }

    }
}

@Composable
fun DefaultAppBar(
    onAddClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Measurements"
            )
        },
        actions = {
            IconButton(
                onClick = { onAddClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Icon",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = { onSaveClicked() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_save_24),
                    contentDescription = "Add Icon",
                    tint = Color.White
                )
            }
        }
    )
}


@Composable
@Preview
fun SizeScreenPreview() {
    SizeScreen(sizeViewModel = SizeViewModel())
}