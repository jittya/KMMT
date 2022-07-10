package com.kmmt.injector.koin

import com.jittyandiyan.mobile.KMMTDB
import com.kmmt.analytics.core.AppInfo
import com.kmmt.analytics.platforms.mixpanel.analyticsMixpanelModule
import com.kmmt.analytics.platforms.uxcam.analyticsUXCamModule
import com.kmmt.common.expectations.Application
import com.kmmt.domain.demo.cache.BreedListCache
import com.kmmt.domain.demotvshowsearch.repository.ITVShowSearchRepository
import com.kmmt.domain.demotvshowsearch.repository.TVShowSearchRepositoryTVMazeAPI
import com.kmmt.domain.demotvshowsearch.usecase.TVShowSearchRepoUseCase
import com.kmmt.persistance.dataSources.breed.BreedDataSource
import com.kmmt.persistance.dataSources.breed.BreedRealmDataSource
import com.kmmt.persistance.dataSources.breed.BreedSQLiteDataSource
import com.kmmt.persistance.keyValueStore.settings.expectations.settings
import com.kmmt.persistance.storage.realm.BreedR
import com.kmmt.persistance.storage.sqlite.expectations.sqliteDriver
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

object Injector {

    lateinit var koinApplication: KoinApplication

    inline fun <reified T : Any> get(): T {
        return koinApplication.koin.get()
    }

    fun initKoin(application: Application, appInfo: AppInfo) {
        koinApplication = startKoin {
            modules(
                module {
                    single { application }
                },
                persistenceModule,
                sqliteDataSource,
                analyticsModule(appInfo),
                domainModule
            )
        }
    }
}

val domainModule = module {
    factory { params -> BreedListCache(backgroundCoroutineScope = params.get(), get()) }
    factory<ITVShowSearchRepository> { TVShowSearchRepositoryTVMazeAPI() }
    factory { TVShowSearchRepoUseCase(get()) }
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

fun analyticsModule(appInfo: AppInfo) = module {
    single { appInfo }
    analyticsMixpanelModule(this, key = "KEY")
    analyticsUXCamModule(this, key = "KEY")
}
