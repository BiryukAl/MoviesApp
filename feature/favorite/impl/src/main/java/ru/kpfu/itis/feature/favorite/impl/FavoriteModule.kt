package ru.kpfu.itis.feature.favorite.impl

import cafe.adriel.voyager.core.registry.screenModule
import org.koin.dsl.module
import ru.kpfu.itis.core.navigation.SharedScreen
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.favorite.api.useCase.AddFavoriteFilmUseCase
import ru.kpfu.itis.feature.favorite.api.useCase.DeleteFavoriteFilmUseCase
import ru.kpfu.itis.feature.favorite.api.useCase.GetAllFavoriteFilmUseCase
import ru.kpfu.itis.feature.favorite.api.useCase.GetFavoriteFilmDetailsUseCase
import ru.kpfu.itis.feature.favorite.impl.data.FavoriteFilmRepositoryImpl
import ru.kpfu.itis.feature.favorite.impl.data.LocalFavoriteDataSource
import ru.kpfu.itis.feature.favorite.impl.data.Mappers
import ru.kpfu.itis.feature.favorite.impl.data.RemoteFilmDataSource
import ru.kpfu.itis.feature.favorite.impl.presentation.FavoritesScreen
import ru.kpfu.itis.feature.favorite.impl.presentation.FavoritesViewModel
import ru.kpfu.itis.feature.favorite.impl.useCase.AddFavoriteFilmUseCaseImpl
import ru.kpfu.itis.feature.favorite.impl.useCase.DeleteFavoriteFilmUseCaseImpl
import ru.kpfu.itis.feature.favorite.impl.useCase.GetAllFavoriteFilmUseCaseImpl
import ru.kpfu.itis.feature.favorite.impl.useCase.GetFavoriteFilmDetailsUseCaseImpl

val favoriteModule = module {
    //Data
    factory<RemoteFilmDataSource> {
        RemoteFilmDataSource.Base(client = get())
    }
    factory<Mappers> {
        Mappers()
    }
    factory<LocalFavoriteDataSource> {
        LocalFavoriteDataSource.Base(realm = get(), mapper = get())
    }
    factory<FavoriteFilmRepository> {
        FavoriteFilmRepositoryImpl(local = get(), remote = get(), mappers = get())
    }
    //UseCase
    factory<AddFavoriteFilmUseCase> {
        AddFavoriteFilmUseCaseImpl(repository = get())
    }
    factory<DeleteFavoriteFilmUseCase> {
        DeleteFavoriteFilmUseCaseImpl(repository = get())
    }
    factory<GetAllFavoriteFilmUseCase> {
        GetAllFavoriteFilmUseCaseImpl(repository = get())
    }
    factory<GetFavoriteFilmDetailsUseCase> {
        GetFavoriteFilmDetailsUseCaseImpl(repository = get())
    }
    //Presentation
    factory<FavoritesViewModel> {
        FavoritesViewModel(
            deleteFavoriteFilmUseCase = get(),
            getAllFavoriteFilmUseCase = get()
        )
    }
}

val favoriteModuleScreen = screenModule {
    register<SharedScreen.FavoritesScreen> {
        FavoritesScreen()
    }
}
