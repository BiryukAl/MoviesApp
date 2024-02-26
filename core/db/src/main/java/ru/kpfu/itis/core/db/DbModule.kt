package ru.kpfu.itis.core.db

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.realmSetOf
import org.koin.dsl.module
import ru.kpfu.itis.core.db.realm.Film

val dbModule = module {
    single<RealmConfiguration> {
        provideRealmConfig()
    }

    single<Realm> {
        provideRealm(
            config = get()
        )
    }
}

fun provideRealm(config: RealmConfiguration): Realm =
    Realm.open(config)

fun provideRealmConfig(): RealmConfiguration =
    RealmConfiguration.Builder(
        schema = setOf(
            Film::class
        )
    ).schemaVersion(
        BuildConfig.VERSION_DB
    )
        .build()

fun provideMockRealmConfig(): RealmConfiguration =
    RealmConfiguration.Builder(
        schema = setOf(
            Film::class
        )
    ).schemaVersion(
        BuildConfig.VERSION_DB
    ).initialData {
        copyToRealm(
            Film().apply {
                kinopoiskId = 263531
                name = "Мстители"
                previewUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/301.jpg"
                imageUrl = "http://kinopoiskapiunofficial.tech/images/posters/kp/263531.jpg"
                genres = realmSetOf("фантастика")
                countries = realmSetOf("США")
                year = 2012
                description = "США, Джосс Уидон(фантастика)"
            }
        )
        copyToRealm(
            Film().apply {
                kinopoiskId = 6039
                name = "Маска"
                previewUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp/6039.jpg"
                imageUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/6039.jpg"
                genres = realmSetOf("комедия")
                countries = realmSetOf("США")
                year = 1994
                description = "Скромный и застенчивый служащий банка чувствует себя "
            }
        )
    }
        .inMemory()
        .build()
