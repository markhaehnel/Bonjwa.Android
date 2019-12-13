package xyz.haehnel.bonjwa.ui.settings

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.layout.Column
import androidx.ui.material.Button
import xyz.haehnel.bonjwa.ui.Screen
import xyz.haehnel.bonjwa.ui.navigateTo

@Composable
fun SettingsScreen(openDrawer: () -> Unit) {
    Column {
        Text(text = "Settings")
        Button(text = "Navigate to Schedule", onClick = {
            navigateTo(
                Screen.Schedule
            )
        })
    }
}