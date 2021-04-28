package com.jittyandiyan.shared.core.expectations

import android.content.Context
import com.jittyandiyan.mobile.KMMTDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Main

internal actual val Dispatchers_Default: CoroutineDispatcher=Dispatchers.Default

actual fun getAppContextAsKoinBean(appContext: Any): Module {
    appContext as Context
    return module {
        single<Context> { appContext }
    }
}

actual val sqlDriverModule: Module
    get() = module {
        single<SqlDriver> { AndroidSqliteDriver(KMMTDB.Schema, get(), "KMMTB.db") }
    }
