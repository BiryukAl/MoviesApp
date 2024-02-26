package ru.kpfu.itis.moviesapp

import org.koin.dsl.module
import ru.kpfu.itis.moviesapp.ui.SplashScreenModel

val appModule = module {
    factory<SplashScreenModel> {
        SplashScreenModel()
    }
}