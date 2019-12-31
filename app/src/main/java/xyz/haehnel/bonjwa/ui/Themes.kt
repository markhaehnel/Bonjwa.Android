package xyz.haehnel.bonjwa.ui

import androidx.ui.graphics.Color
import androidx.ui.material.ColorPalette

val darkTheme = ColorPalette(
    primary = Color(18, 18, 18),
    primaryVariant = Color(24, 24, 24),
    secondary = Color(0xFF1E9761),
    secondaryVariant = Color(12, 12, 12),
    background = Color(18, 18, 18),
    surface = Color(12, 12, 12),
    error = Color(200, 0, 0),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.White
)

val lightTheme = ColorPalette(
    primary = Color(255, 255, 255),
    primaryVariant = Color(255, 255, 255),
    secondary = Color(0xFF1E9761),
    secondaryVariant = Color(245, 245, 245),
    background = Color(255, 255, 255),
    surface = Color(250, 250, 250),
    error = Color(200, 0, 0),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.Black
)

val themeList = listOf(darkTheme, lightTheme)
