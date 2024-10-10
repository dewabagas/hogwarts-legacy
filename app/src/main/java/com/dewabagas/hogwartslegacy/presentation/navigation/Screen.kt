package com.dewabagas.hogwartslegacy.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object School : Screen("school")
}
