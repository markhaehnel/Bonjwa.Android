package xyz.haehnel.bonjwa.ui

import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.material.*
import androidx.ui.material.surface.Surface
import xyz.haehnel.bonjwa.ui.home.HomeScreen

sealed class Screen {
    object Home : Screen()
    object Settings : Screen()
}

@Model
private object BonjwaAppModel {
    var currentScreen: Screen = Screen.Home
}

@Composable
fun BonjwaApp(nightMode: Boolean) {
    MaterialTheme(
        colors = if (nightMode) darkThemeColors else lightThemeColors,
        typography = themeTypography
    ) {
        AppContent()
    }
}

@Composable
private fun AppContent() {
    Crossfade(BonjwaAppModel.currentScreen) { screen ->
        Surface(color = +themeColor { background }) {
            when (screen) {
                is Screen.Home -> HomeScreen { navigateTo(Screen.Settings) }
                is Screen.Settings -> SettingsScreen { navigateTo(Screen.Home) }
            }
        }
    }
}

private fun navigateTo(destination: Screen) {
    BonjwaAppModel.currentScreen = destination
}
