package ru.kpfu.itis.feature.details.impl

import cafe.adriel.voyager.core.registry.screenModule
import org.koin.dsl.module
import ru.kpfu.itis.core.navigation.SharedScreen
import ru.kpfu.itis.feature.details.api.DetailsFilmRepository
import ru.kpfu.itis.feature.details.api.GetFilmDetailsUseCase
import ru.kpfu.itis.feature.details.impl.Navigation.FILM_ID_KOIN_PROPERTY
import ru.kpfu.itis.feature.details.impl.data.DetailsFilmRepositoryImpl
import ru.kpfu.itis.feature.details.impl.data.Mapper
import ru.kpfu.itis.feature.details.impl.data.RemoteDetailsDataSouse
import ru.kpfu.itis.feature.details.impl.presentation.DetailsScreen
import ru.kpfu.itis.feature.details.impl.presentation.DetailsViewModel
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

    //Presentation
    factory<DetailsViewModel> {
        DetailsViewModel(
            filmId = getProperty(FILM_ID_KOIN_PROPERTY),
            getFilmDetailsUseCase = get(),
            appDispatchers = get(),
        )
    }

}

val detailModuleScreen = screenModule {
    register<SharedScreen.DetailsScreen> { provider ->
        DetailsScreen(filmId = provider.id)
    }
}
