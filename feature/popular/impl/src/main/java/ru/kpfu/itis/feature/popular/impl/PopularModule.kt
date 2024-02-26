package ru.kpfu.itis.feature.popular.impl

import cafe.adriel.voyager.core.registry.screenModule
import org.koin.dsl.module
import ru.kpfu.itis.core.navigation.SharedScreen
import ru.kpfu.itis.feature.popular.api.GetPopularFilmsUseCase
import ru.kpfu.itis.feature.popular.api.PopularRepository
import ru.kpfu.itis.feature.popular.impl.data.Mappers
import ru.kpfu.itis.feature.popular.impl.data.PopularFilmDataSource
import ru.kpfu.itis.feature.popular.impl.data.PopularRepositoryImpl
import ru.kpfu.itis.feature.popular.impl.presentation.PopularScreen
import ru.kpfu.itis.feature.popular.impl.presentation.PopularViewModel
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
    //Presentation
    factory<PopularViewModel> {
        PopularViewModel(
            getPopularFilmsUseCase = get(),
            addFavoriteFilmUseCase = get()
        )
    }
}

val popularModuleScreen = screenModule {
    register<SharedScreen.PopularScreen> {
        PopularScreen()
    }
}
