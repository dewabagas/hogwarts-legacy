package com.dewabagas.hogwartslegacy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dewabagas.hogwartslegacy.presentation.school.SchoolScreen
import com.dewabagas.hogwartslegacy.presentation.students.StudentListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(route = Screen.Dashboard.route) {
            StudentListScreen()
        }
        composable(route = Screen.School.route) {
            SchoolScreen()
        }
    }
}
