package ru.kpfu.itis.feature.popular.impl

import org.koin.dsl.module
import ru.kpfu.itis.feature.popular.api.GetPopularFilmsUseCase
import ru.kpfu.itis.feature.popular.api.PopularRepository
import ru.kpfu.itis.feature.popular.impl.data.Mappers
import ru.kpfu.itis.feature.popular.impl.data.PopularFilmDataSource
import ru.kpfu.itis.feature.popular.impl.data.PopularRepositoryImpl
import ru.kpfu.itis.feature.popular.impl.useCase.GetPopularFilmsUseCaseImpl

val popularModule = module {
    //Data
    factory<PopularFilmDataSource> {
        PopularFilmDataSource.Base(client = get())
    }
    factory<PopularRepository> {
        PopularRepositoryImpl(network = get(), local = get(), mapper = Mappers())
    }
    //UseCase
    factory<GetPopularFilmsUseCase> {
        GetPopularFilmsUseCaseImpl(repository = get())
    }
}
