package com.dantariun.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object FaceDetection : Screen("face_detection")
}
