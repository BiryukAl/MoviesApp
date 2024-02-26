package ru.kpfu.itis.core.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreen : ScreenProvider {
    data object PopularScreen : SharedScreen()
    data object SearchScreen : SharedScreen()
    data object FavoritesScreen : SharedScreen()
    data class DetailsScreen(val id: Int) : SharedScreen()

}
