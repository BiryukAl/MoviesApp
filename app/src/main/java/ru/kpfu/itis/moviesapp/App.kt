package ru.kpfu.itis.moviesapp

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.kpfu.itis.core.db.dbModule
import ru.kpfu.itis.core.network.networkModule
import ru.kpfu.itis.core.util.utilModule
import ru.kpfu.itis.feature.details.impl.detailModule
import ru.kpfu.itis.feature.details.impl.detailModuleScreen
import ru.kpfu.itis.feature.favorite.impl.favoriteModule
import ru.kpfu.itis.feature.favorite.impl.favoriteModuleScreen
import ru.kpfu.itis.feature.popular.impl.popularModule
import ru.kpfu.itis.feature.popular.impl.popularModuleScreen
import ru.kpfu.itis.feature.search.impl.searchModule
import ru.kpfu.itis.feature.search.impl.searchModuleScreen
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                appModule,
                dbModule,
                networkModule,
                detailModule,
                favoriteModule,
                popularModule,
                searchModule,
                utilModule,
            )
        }

        ScreenRegistry {
            detailModuleScreen()
            favoriteModuleScreen()
            popularModuleScreen()
            searchModuleScreen()
        }
    }
}
