package ru.kpfu.itis.core.util

import org.koin.dsl.module

val utilModule = module {
    single<AppDispatchers> {
        AppDispatchers.Base()
    }
}
