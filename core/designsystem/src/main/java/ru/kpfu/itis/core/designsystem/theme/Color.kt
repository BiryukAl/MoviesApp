package ru.kpfu.itis.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalColor = staticCompositionLocalOf<MoviesAppColor> {
    error("No color provided")
}

// Light Scheme
val PrimaryLight = Color(0xFF0094FF)
val OnPrimaryLight = Color(0xFFFFFFFF)
val SecondaryLight = Color(0xFFDEEFFF)
val OnSecondaryLight = Color(0xFF0094FF)
val BackgroundLight = Color(0xFFFDFCFF)
val PrimaryTextLight = Color(0xFF1A1C1E)
val SecondaryTextLight = Color(0xFF73777F)
val SurfaceLight = Color(0xFFFDFCFF)

// DarkScheme
val PrimaryDark = Color(0xFFA2C9FF)
val OnPrimaryDark = Color(0xFF00315B)
val SecondaryDark = Color(0xFF004881)
val OnSecondaryDark = Color(0xFFD3E4FF)
val BackgroundDark = Color(0xFF1A1C1E)
val PrimaryTextDark = Color(0xFFE3E2E6)
val SecondaryTextDark = Color(0xFF8D9199)
val SurfaceDark = Color(0xFF1A1C1E)

data class MoviesAppColor(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val surface: Color
)

@Composable
fun provideMoviesAppColor(isDarkTheme: Boolean) = when (isDarkTheme) {
    false -> MoviesAppColor(
        primary = PrimaryLight,
        onPrimary = OnPrimaryLight,
        secondary = SecondaryLight,
        onSecondary = OnSecondaryLight,
        background = BackgroundLight,
        primaryText = PrimaryTextLight,
        secondaryText = SecondaryTextLight,
        surface = SurfaceLight
    )

    true -> MoviesAppColor(
        primary = PrimaryDark,
        onPrimary = OnPrimaryDark,
        secondary = SecondaryDark,
        onSecondary = OnSecondaryDark,
        background = BackgroundDark,
        primaryText = PrimaryTextDark,
        secondaryText = SecondaryTextDark,
        surface = SurfaceDark
    )
}
