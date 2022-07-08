package com.kmmt.persistance.storage.sqlite.expectations

import com.jittyandiyan.mobile.KMMTDB
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual val sqliteDriver: SqlDriver
    get() = NativeSqliteDriver(KMMTDB.Schema, "KMMTB.db")