@file:JvmName("AndroidThemeKt")
package com.boutiq.theme

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
actual fun PlatformTheme(
    lightColorScheme: androidx.compose.material3.ColorScheme,
    typography: Typography,
    shapes: Shapes,
    content: @Composable () -> Unit
) {
    MaterialExpressiveTheme(
        colorScheme = lightColorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
