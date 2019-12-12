package xyz.haehnel.bonjwa.ui

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import xyz.haehnel.bonjwa.ui.home.HomeScreen

@Composable
fun BonjwaApp() {
    MaterialTheme(colors = appTheme) {
        AppContent()
    }
}

@Composable
private fun AppContent() {
    Crossfade(BonjwaAppStatus.currentScreen) { screen ->
        Surface(color = (+MaterialTheme.colors()).background) {
            when (screen) {
                is Screen.Home -> HomeScreen()
                is Screen.Settings -> SettingsScreen()
            }
        }
    }
}
