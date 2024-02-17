package ru.kpfu.itis.feature.details.impl

import org.koin.dsl.module
import ru.kpfu.itis.feature.details.api.DetailsFilmRepository
import ru.kpfu.itis.feature.details.api.GetFilmDetailsUseCase
import ru.kpfu.itis.feature.details.impl.data.DetailsFilmRepositoryImpl
import ru.kpfu.itis.feature.details.impl.data.Mapper
import ru.kpfu.itis.feature.details.impl.data.RemoteDetailsDataSouse
import ru.kpfu.itis.feature.details.impl.useCase.GetDetailFilmUseCaseImpl

val detailModule = module {

    // Data
    factory<RemoteDetailsDataSouse> {
        RemoteDetailsDataSouse.Base(client = get())
    }

    factory<DetailsFilmRepository> {
        DetailsFilmRepositoryImpl(remote = get(), local = get(), mapper = Mapper())
    }

    // UseCase
    factory<GetFilmDetailsUseCase> {
        GetDetailFilmUseCaseImpl(repository = get())
    }
}
