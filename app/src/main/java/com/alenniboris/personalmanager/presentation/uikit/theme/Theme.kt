package com.alenniboris.personalmanager.presentation.uikit.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xff404040),
    background = Color(0xff404040),
    onBackground = Color(0xffffffff),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xffdbdbdb),
    background = Color(0xffdbdbdb),
    onBackground = Color(0xff000000)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun PersonalManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val context = LocalContext.current
    val themeModeInit = remember { context.getLastThemeAndApply(isSystemDarkMode = darkTheme) }

    val colorScheme by remember(
        key1 = currentThemeMode.collectAsStateWithLifecycle().value.isThemeDark
    ) {
        mutableStateOf(LightColorScheme.copy())
    }


    val view = LocalView.current
    if (!view.isInEditMode) {

        val themeMode = currentThemeMode.collectAsStateWithLifecycle()

        SideEffect {
            val window = (view.context as Activity).window

            val appBarColor = Color.Transparent

            window.statusBarColor = topBarColor.toArgb()
            window.navigationBarColor = appColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                themeMode.value is ThemeMode.Light
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                themeMode.value is ThemeMode.Dark
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}