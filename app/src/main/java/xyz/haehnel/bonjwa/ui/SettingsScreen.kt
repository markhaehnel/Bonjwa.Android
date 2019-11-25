package xyz.haehnel.bonjwa.ui

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.layout.Column
import androidx.ui.material.Button

@Composable
fun SettingsScreen() {
    Column {
        Text(text = "Settings")
        Button(text = "Navigate to Home", onClick = { navigateTo(Screen.Home) })
    }
}