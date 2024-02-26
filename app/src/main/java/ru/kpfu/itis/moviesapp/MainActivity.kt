package ru.kpfu.itis.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.android.ext.android.inject
import ru.kpfu.itis.core.designsystem.MoviesAppTheme
import ru.kpfu.itis.moviesapp.ui.NavHost
import ru.kpfu.itis.moviesapp.ui.SplashScreenModel

class MainActivity : ComponentActivity() {


    private val splashScreenModel by inject<SplashScreenModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !splashScreenModel.isLoading.value
        }
        setContent {
            MoviesAppTheme {
                NavHost()
            }
        }
    }
}
