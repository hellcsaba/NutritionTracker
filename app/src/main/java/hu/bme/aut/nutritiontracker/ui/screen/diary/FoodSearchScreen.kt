package hu.bme.aut.nutritiontracker.ui.screen.diary

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hu.bme.aut.nutritiontracker.data.Food
import hu.bme.aut.nutritiontracker.showToast
import hu.bme.aut.nutritiontracker.ui.screen.DefaultAppBar
import hu.bme.aut.nutritiontracker.ui.screen.searchwidget.SearchAppBar
import hu.bme.aut.nutritiontracker.ui.screen.searchwidget.SearchWidgetState
import hu.bme.aut.nutritiontracker.ui.screen.size.AddMeasurementDialog

const val TAG = "FoodSearchScreen"

@Composable
fun FoodSearchScreen(diaryViewModel: DiaryViewModel, navController: NavController) {
    val searchWidgetState by diaryViewModel.searchWidgetState
    val searchTextState by diaryViewModel.searchTextState
    val searchedFoodResult by diaryViewModel.searchedFoodResult.observeAsState()
    var openDialog by rememberSaveable { mutableStateOf(false) }
    var measure by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            FoodSearchAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    diaryViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    diaryViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    diaryViewModel.getFoodBySearch(it)
                },
                onSearchTriggered = {
                    diaryViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        }
    ) {
        if(openDialog){
            AddConsumedFoodDialog(
                onConfirmClicked = {
                    if(measure.isNotEmpty()) {
                        diaryViewModel.addConsumedFood(measure)
                        diaryViewModel.calculateConsumedMacrosAndKcal()
                        measure = ""
                    }
                    openDialog = false
                },
                onDismiss = {
                    measure = ""
                    openDialog = false
                },
                measure = measure,
                onMeasureChanged = {
                    measure = it
                },
                food = diaryViewModel.selectedFood)
        }
        LazyColumn(
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            searchedFoodResult?.let { result ->
                result.hints?.let {  hint ->
                    items(items = hint) {
                        FoodSearchItem(food = it.food!!,
                            onClick = {
                                openDialog = true
                                diaryViewModel.selectedFood = it.food
                                Log.d(TAG, "OnClick")
                            }
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun FoodSearchAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                title = "Search foods",
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}