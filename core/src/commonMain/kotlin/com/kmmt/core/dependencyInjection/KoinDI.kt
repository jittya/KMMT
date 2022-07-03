package com.kmmt.core.dependencyInjection

import com.jittyandiyan.mobile.KMMTDB
import com.kmmt.core.platform.expectations.getAppContextAsKoinBean
import com.kmmt.core.platform.expectations.keyValueStore
import com.kmmt.persistance.dataSources.breed.BreedDataSource
import com.kmmt.persistance.dataSources.breed.BreedRealmDataSource
import com.kmmt.persistance.dataSources.breed.BreedSQLiteDataSource
import com.kmmt.persistance.database.realm.BreedR
import com.kmmt.persistance.expectations.sqlDriverModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module


fun initKoin(context: Any): KoinApplication {
    return startKoin {
        modules(
            getAppContextAsKoinBean(context),
            sqlDriverModule,
            coreModules
        )
    }
}

val coreModules = module {
    single { KMMTDB(get()) }
    single { keyValueStore }

    //Switch the datasource
    var useSQLite = false
    if (useSQLite) {
        single<BreedDataSource> { BreedSQLiteDataSource(get()) }
    } else {
        single<BreedDataSource> { BreedRealmDataSource(BreedR()) }
    }

}
