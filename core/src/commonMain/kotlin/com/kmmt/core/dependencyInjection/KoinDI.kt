package com.kmmt.core.dependencyInjection

import com.jittyandiyan.mobile.KMMTDB
import com.kmmt.core.platform.expectations.getAppContextAsKoinBean
import com.kmmt.persistance.dataSources.breed.BreedDataSource
import com.kmmt.persistance.dataSources.breed.BreedRealmDataSource
import com.kmmt.persistance.dataSources.breed.BreedSQLiteDataSource
import com.kmmt.persistance.database.realm.BreedR
import com.kmmt.persistance.database.sqlite.expectations.sqliteDriver
import com.kmmt.persistance.keyValueStore.settings.expectations.settings
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module


fun initKoin(context: Any): KoinApplication {
    return startKoin {
        modules(
            getAppContextAsKoinBean(context),
            persistenceModule,
            coreModules
        )
    }
}

val persistenceModule = module {
    single { sqliteDriver }
    single { settings }
}

val coreModules = module {
    single { KMMTDB(get()) }
    //Switch the datasource
    var useSQLite = true
    if (useSQLite) {
        single<BreedDataSource> { BreedSQLiteDataSource(get()) }
    } else {
        single<BreedDataSource> { BreedRealmDataSource(BreedR()) }
    }

}
