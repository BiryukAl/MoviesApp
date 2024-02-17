package ru.kpfu.itis.feature.search.impl

import org.koin.dsl.module
import ru.kpfu.itis.feature.search.api.GetFilmsByQueryUseCase
import ru.kpfu.itis.feature.search.api.SearchFilmRepository
import ru.kpfu.itis.feature.search.impl.data.Mapper
import ru.kpfu.itis.feature.search.impl.data.SearchFilmDataSource
import ru.kpfu.itis.feature.search.impl.data.SearchFilmRepositoryImpl
import ru.kpfu.itis.feature.search.impl.useCase.GetFilmsByQueryUseCaseImpl

val searchModule = module {
    //Data
    factory<SearchFilmDataSource> {
        SearchFilmDataSource.Base(client = get())
    }
    factory<SearchFilmRepository> {
        SearchFilmRepositoryImpl(
            network = get(), mapper = Mapper()
        )
    }
    //UseCase
    factory<GetFilmsByQueryUseCase> {
        GetFilmsByQueryUseCaseImpl(repository = get())
    }
}
