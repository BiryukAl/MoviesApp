package ru.kpfu.itis.core.designsystem


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.kpfu.itis.core.designsystem.theme.BackgroundDark
import ru.kpfu.itis.core.designsystem.theme.BackgroundLight
import ru.kpfu.itis.core.designsystem.theme.LocalColor
import ru.kpfu.itis.core.designsystem.theme.LocalTypography
import ru.kpfu.itis.core.designsystem.theme.MoviesAppColor
import ru.kpfu.itis.core.designsystem.theme.MoviesAppTypography
import ru.kpfu.itis.core.designsystem.theme.OnPrimaryDark
import ru.kpfu.itis.core.designsystem.theme.OnPrimaryLight
import ru.kpfu.itis.core.designsystem.theme.OnSecondaryDark
import ru.kpfu.itis.core.designsystem.theme.OnSecondaryLight
import ru.kpfu.itis.core.designsystem.theme.PrimaryDark
import ru.kpfu.itis.core.designsystem.theme.PrimaryLight
import ru.kpfu.itis.core.designsystem.theme.SecondaryDark
import ru.kpfu.itis.core.designsystem.theme.SecondaryLight
import ru.kpfu.itis.core.designsystem.theme.SurfaceDark
import ru.kpfu.itis.core.designsystem.theme.SurfaceLight
import ru.kpfu.itis.core.designsystem.theme.provideMoviesAppColor
import ru.kpfu.itis.core.designsystem.theme.provideMoviesAppTypography


object MoviesAppTheme {
    val type: MoviesAppTypography
        @Composable get() = LocalTypography.current

    val color: MoviesAppColor
        @Composable get() = LocalColor.current
}

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight
)

@Composable
private fun SystemUiColors(
    systemUiController: SystemUiController,
    colorScheme: ColorScheme,
    darkTheme: Boolean
) {
    SideEffect {
        systemUiController.setStatusBarColor(
            color = colorScheme.background,
            darkIcons = !darkTheme
        )
        systemUiController.setNavigationBarColor(
            color = colorScheme.background,
            darkIcons = !darkTheme
        )
    }
}




@Composable
fun MoviesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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

    val moviesAppTypography = provideMoviesAppTypography()
    val moviesAppColor = provideMoviesAppColor(darkTheme)

    CompositionLocalProvider(
        values = arrayOf(
            LocalTypography provides moviesAppTypography,
            LocalColor provides moviesAppColor
        ),
        content = content
    )

    SystemUiColors(
        systemUiController = rememberSystemUiController(),
        colorScheme = colorScheme,
        darkTheme = darkTheme
    )
}