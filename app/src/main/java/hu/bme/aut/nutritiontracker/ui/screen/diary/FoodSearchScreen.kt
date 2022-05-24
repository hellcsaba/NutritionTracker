package hu.bme.aut.nutritiontracker.ui.screen.diary

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hu.bme.aut.nutritiontracker.ui.screen.DefaultAppBar
import hu.bme.aut.nutritiontracker.ui.screen.searchwidget.SearchAppBar
import hu.bme.aut.nutritiontracker.ui.screen.searchwidget.SearchWidgetState

const val TAG = "FoodSearchScreen"

@Composable
fun FoodSearchScreen(diaryViewModel: DiaryViewModel, navController: NavController) {
    val searchWidgetState by diaryViewModel.searchWidgetState
    val searchTextState by diaryViewModel.searchTextState
    val searchedFoodResult by diaryViewModel.searchedFoodResult.observeAsState()

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
        LazyColumn(
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            searchedFoodResult?.let { result ->
                result.hints?.let {  hint ->
                    items(items = hint) {
                        DiaryPreviewItem(food = it.food,
                            onClick = {
                                Log.d(TAG, "OnClick")
//                                diaryViewModel.getRecipeDetail(recipe.id!!)
//                                navController.navigate(route = "recipe_detail_screen/"+recipe.id)
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