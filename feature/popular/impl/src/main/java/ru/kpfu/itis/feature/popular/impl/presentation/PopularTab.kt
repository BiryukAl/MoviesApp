package ru.kpfu.itis.feature.popular.impl.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object PopularTab : Tab {

    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = 1u,
                title = "Popular",
                icon = null
            )
        }

    @Composable
    override fun Content() {
        Navigator(screen = PopularScreen())
//        { navigator ->
//            SlideTransition(navigator = navigator)
//        }
    }
}
