package ru.kpfu.itis.feature.favorite.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.kpfu.itis.feature.favorite.impl.presentation.FavoritesScreen

object FavoriteTab : Tab {
    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = 2u,
                title = "Favorite",
                icon = null
            )
        }

    @Composable
    override fun Content() {
        Navigator(screen = FavoritesScreen())
//        { navigator ->
//            SlideTransition(navigator = navigator)
//        }
    }

}
