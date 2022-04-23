package hu.bme.aut.nutritiontracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import hu.bme.aut.nutritiontracker.ui.screen.*


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
            SizeScreen()
        }
        composable(route = BottomBarScreen.Plan.route) {
            PlanScreen()
        }
        composable(route = BottomBarScreen.Recipe.route) {
            RecipeScreen()
        }
    }
}