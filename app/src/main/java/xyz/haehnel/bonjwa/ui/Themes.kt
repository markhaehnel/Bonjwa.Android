package xyz.haehnel.bonjwa.ui

import androidx.ui.graphics.Color
import androidx.ui.material.MaterialColors

val lightThemeColors = MaterialColors(
    primary = Color.White,
    primaryVariant = Color.White,
    onPrimary = Color.Black,
    secondary = Color(30, 151, 97),
    onSecondary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(200, 0, 0),
    onError = Color.White
)

val darkThemeColors = MaterialColors(
    primary = Color(18, 18, 18),
    primaryVariant = Color(24, 24, 24),
    onPrimary = Color.White,
    secondary = Color(0xFF1E9761),
    onSecondary = Color.White,
    background = Color(18, 18, 18),
    onBackground = Color.White,
    surface = Color(12, 12, 12),
    onSurface = Color.White,
    error = Color(200, 0, 0),
    onError = Color.White
)