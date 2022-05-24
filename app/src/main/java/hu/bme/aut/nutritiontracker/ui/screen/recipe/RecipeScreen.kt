package hu.bme.aut.nutritiontracker.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.nutritiontracker.data.RecipeListResult
import hu.bme.aut.nutritiontracker.ui.screen.recipe.RecipePreviewItem
import hu.bme.aut.nutritiontracker.ui.screen.recipe.RecipeViewModel
import hu.bme.aut.nutritiontracker.ui.screen.searchwidget.SearchWidgetState
import hu.bme.aut.nutritiontracker.ui.screen.searchwidget.SearchAppBar

@Composable
fun RecipeScreen(recipeViewModel: RecipeViewModel, navController: NavController) {
    val searchWidgetState by recipeViewModel.searchWidgetState
    val searchTextState by recipeViewModel.searchTextState
    val recipes: RecipeListResult? by recipeViewModel.getRecipes("").observeAsState()

    Scaffold(
        topBar = {
            RecipeAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    recipeViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    recipeViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    recipeViewModel.getRecipesList(it)
                },
                onSearchTriggered = {
                    recipeViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            recipes?.let { list->
                items(items = list.results!!) { recipe ->
                    RecipePreviewItem(recipe = recipe,
                        onClick = {
                            recipeViewModel.getRecipeDetail(recipe.id!!)
                            navController.navigate(route = "recipe_detail_screen/"+recipe.id)
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun RecipeAppBar(
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



@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Recipes"
            )
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }
    )
}




@Composable
@Preview
fun RecipeScreenPreview() {
    RecipeScreen(RecipeViewModel(), navController = rememberNavController())
}