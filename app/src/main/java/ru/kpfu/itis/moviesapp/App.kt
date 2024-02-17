package ru.kpfu.itis.moviesapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.kpfu.itis.core.db.dbModule
import ru.kpfu.itis.core.network.networkModule
import ru.kpfu.itis.feature.details.impl.detailModule
import ru.kpfu.itis.feature.favorite.impl.favoriteModule
import ru.kpfu.itis.feature.popular.impl.popularModule
import ru.kpfu.itis.feature.search.impl.searchModule
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                dbModule,
                networkModule,
                detailModule,
                favoriteModule,
                popularModule,
                searchModule
            )
        }
    }
}