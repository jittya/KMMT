package com.jittyandiyan.shared.core.dependencyInjection

import com.jittyandiyan.mobile.KMMTDB
import com.jittyandiyan.shared.core.expectations.getAppContextAsKoinBean
import com.jittyandiyan.shared.core.expectations.sqlDriverModule
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
}
