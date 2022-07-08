package com.kmmt.injector.koin

import com.jittyandiyan.mobile.KMMTDB
import com.kmmt.common.expectations.Application
import com.kmmt.persistance.dataSources.breed.BreedDataSource
import com.kmmt.persistance.dataSources.breed.BreedRealmDataSource
import com.kmmt.persistance.dataSources.breed.BreedSQLiteDataSource
import com.kmmt.persistance.storage.realm.BreedR
import com.kmmt.persistance.storage.sqlite.expectations.sqliteDriver
import com.kmmt.persistance.keyValueStore.settings.expectations.settings
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

object Injector {

    lateinit var koinApplication: KoinApplication

    fun initKoin(application: Application) {
        koinApplication = startKoin {
            modules(
                module {
                    single { application }
                },
                persistenceModule,
                sqliteDataSource
            )
        }
    }
}

val persistenceModule = module {
    single { sqliteDriver }
    single { settings }
}

val sqliteDataSource = module {
    single { KMMTDB(get()) }
    single<BreedDataSource> { BreedSQLiteDataSource(get()) }
}

val realmDataSource = module {
    single<BreedDataSource> { BreedRealmDataSource(BreedR()) }
}
