package hu.bme.aut.nutritiontracker

sealed class Screen(val route: String){
    object Login: Screen(route = "login_screen")
    object SignUp: Screen(route = "signup_screen")
    object MainScreen: Screen(route = "main_screen")
}
