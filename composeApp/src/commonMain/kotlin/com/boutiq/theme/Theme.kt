@file:JvmName("CommonThemeKt")
@file:OptIn(ExperimentalResourceApi::class)
package com.boutiq.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import boutiq.composeapp.generated.resources.Res
import boutiq.composeapp.generated.resources.quicksand_bold
import boutiq.composeapp.generated.resources.quicksand_regular
import kotlin.jvm.JvmName
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

@Composable
expect fun PlatformTheme(
    lightColorScheme: androidx.compose.material3.ColorScheme,
    typography: Typography,
    shapes: Shapes,
    content: @Composable () -> Unit
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val lightColorScheme = lightColorScheme(
        // Primary colors
        primary = Color(0xFFE21F4E),         // Your primary pink-red
        onPrimary = Color.White,             // White text on primary
        primaryContainer = Color(0xFFFFDADE), // Light pink container
        onPrimaryContainer = Color(0xFF400012), // Dark pink for text on container

        // Secondary colors (complementary to primary)
        secondary = Color(0xFF775657),       // Muted dusty rose
        onSecondary = Color.White,           // White text on secondary
        secondaryContainer = Color(0xFFFFDADA), // Light pink container
        onSecondaryContainer = Color(0xFF2C1516), // Dark text on container

        // Tertiary colors (accent)
        tertiary = Color(0xFF745B00),        // Gold accent (complementary)
        onTertiary = Color.White,            // White text on tertiary
        tertiaryContainer = Color(0xFFFFDF94), // Light gold container
        onTertiaryContainer = Color(0xFF261900), // Dark text on container

        // Background colors
        background = Color(0xFFFFFFFF),      // Pure white background
        onBackground = Color(0xFF201A1A),    // Slightly softened black
        surface = Color(0xFFF8F8F8),         // Slightly warm white
        onSurface = Color(0xFF201A1A),       // Slightly softened black
        surfaceVariant = Color(0xFFF3DDDF),  // Subtle pink tint
        onSurfaceVariant = Color(0xFF534344), // Muted dark color

        // Other important colors
        error = Color(0xFFBA1A1A),           // Standard error red (slightly different from primary)
        onError = Color.White,               // White text on error
        errorContainer = Color(0xFFFFDAD6),  // Light error container
        onErrorContainer = Color(0xFF410002), // Dark text on error container

        // Utility colors
        outline = Color(0xFFFFDADA),         // Subtle border color
        outlineVariant = Color(0xFFFFDADA),  // Lighter border option
        scrim = Color(0xFF000000),           // Standard scrim
        inverseSurface = Color(0xFF362F2F),  // Dark surface for contrast
        inverseOnSurface = Color(0xFFFBEEED), // Light text for inverse
        inversePrimary = Color(0xFFFFB2BB)    // Light primary for dark themes
    )

    val regularFont = FontFamily(Font(Res.font.quicksand_regular))
    val boldFont = FontFamily(Font(Res.font.quicksand_bold))

    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = regularFont,
        ),
        bodyLarge = TextStyle(
            fontFamily = regularFont,
        ),
        bodySmall = TextStyle(
            fontFamily = regularFont,
        ),
        headlineMedium = TextStyle(
            fontFamily = boldFont,
        ),
        headlineSmall = TextStyle(
            fontFamily = boldFont,
        ),
        headlineLarge = TextStyle(
            fontFamily = boldFont,
        ),
        titleMedium = TextStyle(
            fontFamily = regularFont,
        ),
        titleLarge = TextStyle(
            fontFamily = regularFont,
        ),
        titleSmall = TextStyle(
            fontFamily = regularFont,
        ),
        labelLarge = TextStyle(
            fontFamily = regularFont,
        ),
        labelMedium = TextStyle(
            fontFamily = regularFont,
        ),
        labelSmall = TextStyle(
            fontFamily = regularFont,
        ),
    )

    val shapes = Shapes(
        extraSmall = RoundedCornerShape(4.dp),
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(12.dp),
        large = RoundedCornerShape(16.dp),
        extraLarge = RoundedCornerShape(24.dp)
    )

    // Using platform-specific theme implementation
    // Android will use MaterialExpressiveTheme, iOS will use MaterialTheme
    PlatformTheme(
        lightColorScheme = lightColorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
