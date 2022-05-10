package hu.bme.aut.nutritiontracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import hu.bme.aut.nutritiontracker.MainActivity
import hu.bme.aut.nutritiontracker.ui.screen.*
import hu.bme.aut.nutritiontracker.ui.screen.recipe.RecipeViewModel
import hu.bme.aut.nutritiontracker.ui.screen.size.SizeViewModel


@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Diary.route
    ) {
        composable(route = BottomBarScreen.Diary.route) {
            DiaryScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Size.route) {
            SizeScreen(sizeViewModel = SizeViewModel())
        }
        composable(route = BottomBarScreen.Plan.route) {
            PlanScreen()
        }
        composable(route = BottomBarScreen.Recipe.route) {
            val recipeViewModel = RecipeViewModel()
            recipeViewModel.attach((LocalContext.current as LifecycleOwner))
            RecipeScreen(recipeViewModel = recipeViewModel)
        }
    }
}