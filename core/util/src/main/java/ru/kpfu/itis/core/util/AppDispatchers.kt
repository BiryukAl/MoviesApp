package ru.kpfu.itis.core.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

interface AppDispatchers {
    val main: MainCoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

    class Base internal constructor() : AppDispatchers {
        override val main: MainCoroutineDispatcher = Dispatchers.Main
        override val default: CoroutineDispatcher = Dispatchers.Default
        override val io: CoroutineDispatcher = Dispatchers.IO
        override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
    }
}