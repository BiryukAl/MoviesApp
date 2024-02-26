package ru.kpfu.itis.feature.search.impl

import cafe.adriel.voyager.core.registry.screenModule
import org.koin.dsl.module
import ru.kpfu.itis.core.navigation.SharedScreen
import ru.kpfu.itis.feature.search.api.GetFilmsByQueryUseCase
import ru.kpfu.itis.feature.search.api.SearchFilmRepository
import ru.kpfu.itis.feature.search.impl.data.Mapper
import ru.kpfu.itis.feature.search.impl.data.SearchFilmDataSource
import ru.kpfu.itis.feature.search.impl.data.SearchFilmRepositoryImpl
import ru.kpfu.itis.feature.search.impl.presentation.SearchScreen
import ru.kpfu.itis.feature.search.impl.presentation.SearchViewModel
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
    //Presentation
    factory<SearchViewModel> {
        SearchViewModel(getFilmsByQueryUseCase = get())
    }
}

val searchModuleScreen = screenModule {
    register<SharedScreen.SearchScreen> {
        SearchScreen()
    }
}
