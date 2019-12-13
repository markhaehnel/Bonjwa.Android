package xyz.haehnel.bonjwa.ui

import androidx.compose.Model

sealed class Screen {
    object Schedule : Screen()
    object Settings : Screen()
}

@Model
object BonjwaStatus {
var currentScreen: Screen = Screen.Schedule
}

fun navigateTo(destination: Screen) {
    BonjwaStatus.currentScreen = destination
}