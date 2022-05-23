package hu.bme.aut.nutritiontracker


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.nutritiontracker.ui.MainScreen
import hu.bme.aut.nutritiontracker.ui.navigation.BottomBarScreen
import hu.bme.aut.nutritiontracker.ui.screen.*
import hu.bme.aut.nutritiontracker.ui.screen.authentication.AuthenticationViewModel


@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()
    val navBarNavController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Login.route
        ) {
            val authenticationViewModel = AuthenticationViewModel()
            authenticationViewModel.attach(LocalContext.current as LifecycleOwner)
            LoginScreen(authenticationViewModel, navController = navController)
        }
        composable(
            route = Screen.SignUp.route
        ) {
            SignUpScreen(navController = navController)
        }
        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen(navController = navBarNavController)
        }
    }
}


