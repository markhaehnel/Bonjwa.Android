package xyz.haehnel.bonjwa.ui

import androidx.ui.graphics.Color
import androidx.ui.material.MaterialColors

val lightThemeColors = MaterialColors(
    primary = Color.White,
    primaryVariant = Color.White,
    onPrimary = Color.Black,
    secondary = Color.White,
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color.Red,
    onError = Color.White
)

val darkThemeColors = MaterialColors(
    primary = Color(18, 18, 18),
    primaryVariant = Color(24, 24, 24),
    onPrimary = Color.White,
    secondary = Color(12, 59, 38),
    onSecondary = Color.White,
    surface = Color(0xFF121212),
    background = Color(18, 18, 18),
    onBackground = Color.White,
    onSurface = Color.White
)