package com.dantariun.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dantariun.presentation.FaceDetectionScreen
import com.dantariun.presentation.HomeScreen

@Composable
fun MorphViewNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDetection = {
                    navController.navigate(Screen.FaceDetection.route)
                }
            )
        }
        composable(Screen.FaceDetection.route) {
            FaceDetectionScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
