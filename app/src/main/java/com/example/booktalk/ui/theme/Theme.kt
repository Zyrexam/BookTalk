package com.example.booktalk.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColorScheme(
    primary = primaryNight,
    secondary = Teal200,
    background = backgroundNight,
    surface = cardNight,
    onPrimary = textNight,
    onSecondary = Teal200,
    onBackground = textNight,
    onSurface = cardNight
)

private val LightColorPalette = lightColorScheme(
    primary = primary,
    secondary = Teal200,
    background = background,
    surface = card,
    onPrimary = text,
    onSecondary = Teal200,
    onBackground = text,
    onSurface = card
)




@Composable
fun BookTalkTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}
