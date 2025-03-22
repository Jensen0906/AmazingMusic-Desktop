package org.may.amazingmusic.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.White,
    primaryVariant = Color(red = 38, green = 44, blue = 91, alpha = 60),
    secondary = Color(red = 224, green = 224, blue = 240),
    onPrimary = Color.DarkGray,
    onSecondary = Color(red = 78, green = 135, blue = 168, alpha = 255),
)

private val LightColorPalette = lightColors(
    primary = MyLightGray,
    primaryVariant = Color(red = 237, green = 237, blue = 237),
    secondary = Color(red = 25, green = 25, blue = 25),
    onPrimary = Color.Black,
    onSecondary = Color(red = 138, green = 209, blue = 250),
)

@Composable
fun AmazingMusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    // Just use LightTheme
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}