package com.cvelez.freemarkettest.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Typography
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.darkColors
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColors(
    primary =   theme_light_primary,
    primaryVariant = theme_light_primaryVariant,
    secondary = theme_light_secondary,
    secondaryVariant = theme_light_secondaryVariant,
    background = theme_light_background,
    surface = theme_light_surface,
    error = theme_light_error,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.White
)

private val LightColorPalette = lightColors(
    primary =   theme_dark_primary,
    primaryVariant = theme_dark_primaryVariant,
    secondary = theme_dark_secondary,
    secondaryVariant = theme_dark_secondaryVariant,
    background = theme_dark_background,
    surface = theme_dark_surface,
    error = theme_dark_error,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.Black
)

@Composable
fun TestMeliTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) LightColorPalette else DarkColorPalette

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    androidx.compose.material.MaterialTheme(
        colors = colors,
        typography = Typography(),
        content = content
    )
}