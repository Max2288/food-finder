package com.example.foodfinder.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
    darkColorScheme(
        primary = Purple80,
        onPrimary = White,
        secondary = PurpleGrey80,
        tertiary = Pink80,
        background = Black,
        onBackground = White,
        surface = Black,
        onSurface = White,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = Purple40,
        onPrimary = Black,
        secondary = PurpleGrey40,
        tertiary = Pink40,
        background = White,
        onBackground = Black,
        surface = White,
        onSurface = Black,
    )

@Composable
fun FoodFinderTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

object ThemeManager {
    var isDarkTheme: MutableState<Boolean> = mutableStateOf(false)
}
