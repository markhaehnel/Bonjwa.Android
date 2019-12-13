package xyz.haehnel.bonjwa.ui

import androidx.compose.Model

sealed class Screen {
    object Home : Screen()
    object Settings : Screen()
}

@Model
object BonjwaStatus {
var currentScreen: Screen = Screen.Home
}

fun navigateTo(destination: Screen) {
    BonjwaStatus.currentScreen = destination
}