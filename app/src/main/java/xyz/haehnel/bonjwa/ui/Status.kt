package xyz.haehnel.bonjwa.ui

import androidx.compose.Model

sealed class Screen {
    object Home : Screen()
    object Settings : Screen()
}

@Model
object BonjwaAppStatus {
var currentScreen: Screen = Screen.Home
}

fun navigateTo(destination: Screen) {
    BonjwaAppStatus.currentScreen = destination
}