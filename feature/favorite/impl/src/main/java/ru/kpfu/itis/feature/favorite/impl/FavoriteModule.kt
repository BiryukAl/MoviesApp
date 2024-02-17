package ru.kpfu.itis.feature.favorite.impl

import org.koin.dsl.module
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.favorite.api.useCase.AddFavoriteFilm
import ru.kpfu.itis.feature.favorite.api.useCase.DeleteFavoriteFilm
import ru.kpfu.itis.feature.favorite.api.useCase.GetAllFavoriteFilm
import ru.kpfu.itis.feature.favorite.api.useCase.GetFavoriteFilmDetails
import ru.kpfu.itis.feature.favorite.impl.data.FavoriteFilmRepositoryImpl
import ru.kpfu.itis.feature.favorite.impl.data.LocalFavoriteDataSource
import ru.kpfu.itis.feature.favorite.impl.data.Mappers
import ru.kpfu.itis.feature.favorite.impl.data.RemoteFilmDataSource
import ru.kpfu.itis.feature.favorite.impl.useCase.AddFavoriteFilmImpl
import ru.kpfu.itis.feature.favorite.impl.useCase.DeleteFavoriteFilmImpl
import ru.kpfu.itis.feature.favorite.impl.useCase.GetAllFavoriteFilmImpl
import ru.kpfu.itis.feature.favorite.impl.useCase.GetFavoriteFilmDetailsImpl

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
    factory<AddFavoriteFilm> {
        AddFavoriteFilmImpl(repository = get())
    }
    factory<DeleteFavoriteFilm> {
        DeleteFavoriteFilmImpl(repository = get())
    }
    factory<GetAllFavoriteFilm> {
        GetAllFavoriteFilmImpl(repository = get())
    }
    factory<GetFavoriteFilmDetails> {
        GetFavoriteFilmDetailsImpl(repository = get())
    }
}
