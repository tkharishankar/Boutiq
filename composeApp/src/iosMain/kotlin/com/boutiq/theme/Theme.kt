package com.boutiq.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

@Composable
actual fun PlatformTheme(
    lightColorScheme: androidx.compose.material3.ColorScheme,
    typography: Typography,
    shapes: Shapes,
    content: @Composable () -> Unit
) {
    // Delegate to the implementation in the ios subdirectory
    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
