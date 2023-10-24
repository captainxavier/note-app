package com.xavier.noteapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Black10,
    onPrimary = White95,
    primaryContainer = Thistle65,
    onPrimaryContainer = Black15,
    inversePrimary = Black10,
    secondary = Black5,
    onSecondary = White95,
    tertiary = Thistle50,
    onTertiary = Black15,
    tertiaryContainer = Thistle65,
    onTertiaryContainer = Black15,
    surface = Black10,
    onSurface = Grey70,
    inverseSurface = Black5,
    inverseOnSurface = Grey25,
    surfaceVariant = Black0,
    onSurfaceVariant = Grey25,
    error = RED15,
    onError = RED65,
    errorContainer = RED65,
    onErrorContainer = RED25,
    background = Black13,
    onBackground = Grey85,
    outline = Grey25,
)

private val LightColorScheme = lightColorScheme(
    primary = White99,
    onPrimary = Black10,
    primaryContainer = Thistle65,
    onPrimaryContainer = Black15,
    inversePrimary = White99,
    secondary = White100,
    onSecondary = Black10,
    tertiary = Thistle50,
    onTertiary = Black15,
    tertiaryContainer = Thistle65,
    onTertiaryContainer = Black15,
    surface = White99,
    onSurface = Grey25,
    inverseSurface = White100,
    inverseOnSurface = Grey25,
    surfaceVariant = White99,
    onSurfaceVariant = Grey25,
    error = RED15,
    onError = RED65,
    errorContainer = RED65,
    onErrorContainer = RED25,
    background = White95,
    onBackground = Black10,
    outline = White99
)

@Composable
fun NoteAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}