package ru.kpfu.itis.core.db

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
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
