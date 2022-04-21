package hu.bme.aut.nutritiontracker.ui.navigation

import hu.bme.aut.nutritiontracker.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Diary : BottomBarScreen(
        route = "diary",
        title = "Diary",
        icon = R.drawable.ic_outline_book_24
    )

    object Size : BottomBarScreen(
        route = "size",
        title = "Size",
        icon = R.drawable.ic_bottomnav_measure
    )

    object Plan : BottomBarScreen(
        route = "plan",
        title = "Plan",
        icon = R.drawable.ic_bottomnav_plan
    )


    object Recipe : BottomBarScreen(
        route = "recipes",
        title = "Recipes",
        icon = R.drawable.ic_bottomnav_recipe
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = R.drawable.ic_outline_person_outline_24
    )
}