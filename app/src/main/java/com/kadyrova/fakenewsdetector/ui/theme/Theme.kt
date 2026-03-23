package com.kadyrova.fakenewsdetector.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EmeraldColorScheme = darkColorScheme(
    primary = EmeraldGreen,
    onPrimary = White,
    primaryContainer = EmeraldDark,
    secondary = EmeraldLight,
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = OnSurface,
    onSurface = OnSurface,
)

@Composable
fun FakenewsdetectorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EmeraldColorScheme,
        typography = Typography,
        content = content
    )
}