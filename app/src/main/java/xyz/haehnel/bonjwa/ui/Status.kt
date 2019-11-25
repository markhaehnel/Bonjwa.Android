package xyz.haehnel.bonjwa.ui

import androidx.compose.Model

/**
 * Class defining the screens we have in the app: home, settings
 */
sealed class Screen {
    object Home : Screen()
    object Settings : Screen()
    //data class ScreenWithParameters(val id: Int) : Screen()
}

@Model
object BonjwaAppStatus {
    var currentScreen: Screen = Screen.Home
}

/**
 * Temporary solution pending navigation support.
 */
fun navigateTo(destination: Screen) {
    BonjwaAppStatus.currentScreen = destination
}