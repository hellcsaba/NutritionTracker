package hu.bme.aut.nutritiontracker.ui.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import hu.bme.aut.nutritiontracker.MainActivity
import hu.bme.aut.nutritiontracker.Screen
import hu.bme.aut.nutritiontracker.ui.screen.*
import hu.bme.aut.nutritiontracker.ui.screen.diary.DiaryViewModel
import hu.bme.aut.nutritiontracker.ui.screen.diary.FoodSearchScreen
import hu.bme.aut.nutritiontracker.ui.screen.profile.ProfileViewModel
import hu.bme.aut.nutritiontracker.ui.screen.recipe.RecipeDetailScreen
import hu.bme.aut.nutritiontracker.ui.screen.recipe.RecipeViewModel
import hu.bme.aut.nutritiontracker.ui.screen.size.SizeViewModel


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun BottomNavGraph(navController: NavHostController) {
    val recipeViewModel = RecipeViewModel()
    val diaryViewModel = DiaryViewModel()
    val sizeViewModel = SizeViewModel()
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Diary.route
    ) {
        composable(route = BottomBarScreen.Diary.route) {
            diaryViewModel.attach(LocalContext.current as LifecycleOwner)
            DiaryScreen(diaryViewModel = diaryViewModel, navController = navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(profileViewModel = ProfileViewModel())
        }
        composable(route = BottomBarScreen.Size.route) {
            sizeViewModel.attach(LocalContext.current as LifecycleOwner)
            SizeScreen(sizeViewModel = sizeViewModel)
        }
        composable(route = BottomBarScreen.Plan.route) {
            PlanScreen()
        }
        composable(route = BottomBarScreen.Recipe.route) {
            recipeViewModel.attach((LocalContext.current as LifecycleOwner))
            RecipeScreen(recipeViewModel = recipeViewModel, navController)
        }
        composable(
            route = Screen.RecipeDetailScreen.route,
            arguments = listOf(navArgument("id"){
                type = NavType.IntType
            })
        ){
            RecipeDetailScreen(recipeViewModel = recipeViewModel)
        }
        composable(route = Screen.FoodSearchScreen.route){
            FoodSearchScreen(diaryViewModel = diaryViewModel, navController = navController)
        }
    }
}