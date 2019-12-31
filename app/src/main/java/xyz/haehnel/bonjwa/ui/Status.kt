package xyz.haehnel.bonjwa.ui

import androidx.compose.Model
import androidx.ui.material.ColorPalette

sealed class Screen {
    object Schedule : Screen()
    object Settings : Screen()
}

@Model
object BonjwaStatus {
    var currentScreen: Screen = Screen.Schedule
    var appTheme: ColorPalette = darkTheme
}

fun navigateTo(destination: Screen) {
    BonjwaStatus.currentScreen = destination
}
