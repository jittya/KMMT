package com.kmmt.persistance.expectations

import com.jittyandiyan.mobile.KMMTDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sqlDriverModule: Module
    get() = module {
        single<SqlDriver> { AndroidSqliteDriver(KMMTDB.Schema, get(), "KMMTB.db") }
    }

actual val Dispatchers_Default: CoroutineDispatcher = Dispatchers.Default