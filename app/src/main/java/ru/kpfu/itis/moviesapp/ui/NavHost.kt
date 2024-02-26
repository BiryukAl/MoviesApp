package ru.kpfu.itis.moviesapp.ui


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ru.kpfu.itis.core.designsystem.MoviesAppTheme
import ru.kpfu.itis.core.designsystem.icon.MoviesAppIcons
import ru.kpfu.itis.core.widget.KinopoiskBottomNavigationItem
import ru.kpfu.itis.feature.favorite.impl.FavoriteTab
import ru.kpfu.itis.feature.popular.impl.presentation.PopularTab


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavHost() {
    TabNavigator(
        tab = PopularTab,
        tabDisposable = {
            TabDisposable(
                navigator = it,
                tabs = listOf(
                    PopularTab,
                    FavoriteTab,
                )
            )
        }
    ) {
        Scaffold(
            content = { CurrentTab() },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    MoviesBottomNavigationItem(
                        modifier = Modifier.weight(1f),
                        tab = PopularTab,
                        filledIcon = MoviesAppIcons.HomeFilled,
                        outlinedIcon = MoviesAppIcons.HomeOutlined
                    )
                    MoviesBottomNavigationItem(
                        modifier = Modifier.weight(1f),
                        tab = FavoriteTab,
                        filledIcon = MoviesAppIcons.FavoritesFilled,
                        outlinedIcon = MoviesAppIcons.FavoritesOutlined
                    )


                }
            }
        )
    }
}

@Composable
fun MoviesBottomNavigationItem(
    modifier: Modifier,
    tab: Tab,
    filledIcon: ImageVector,
    outlinedIcon: ImageVector
) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current == tab


    KinopoiskBottomNavigationItem(
        modifier = modifier,
        text = tab.options.title,
        containerColor = when (isSelected) {
            true -> MoviesAppTheme.color.primary
            false -> MoviesAppTheme.color.secondary
        },
        contentColor = when (isSelected) {
            true -> MoviesAppTheme.color.onPrimary
            false -> MoviesAppTheme.color.onSecondary
        },
        isSelected = isSelected,
        icon = when (isSelected) {
            true -> filledIcon
            false -> outlinedIcon
        },
    ) {
        tabNavigator.current = tab
    }
}
