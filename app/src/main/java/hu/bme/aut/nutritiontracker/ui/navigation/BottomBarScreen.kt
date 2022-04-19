package hu.bme.aut.nutritiontracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Diary : BottomBarScreen(
        route = "diary",
        title = "Diary",
        icon = Icons.Default.Home
    )

    object Size : BottomBarScreen(
        route = "size",
        title = "Size",
        icon = Icons.Default.Person
    )

    object Plan : BottomBarScreen(
        route = "plan",
        title = "Plan",
        icon = Icons.Default.Person
    )


    object Recipe : BottomBarScreen(
        route = "recipes",
        title = "Recipes",
        icon = Icons.Default.Settings
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
}