package com.kmmt.persistance.expectations

import com.jittyandiyan.mobile.KMMTDB
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

//@Suppress("UNRESOLVED_REFERENCE", "TYPE_MISMATCH")
actual val sqlDriverModule: Module
    get() = module { single<SqlDriver> { NativeSqliteDriver(KMMTDB.Schema, "KMMTB.db") } }

actual val Dispatchers_Default: CoroutineDispatcher = Dispatchers.Main