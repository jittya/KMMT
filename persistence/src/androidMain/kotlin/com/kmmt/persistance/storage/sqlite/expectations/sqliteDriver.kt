package com.kmmt.persistance.storage.sqlite.expectations

import com.jittyandiyan.mobile.KMMTDB
import com.kmmt.common.dependencyInjection.Android
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual val sqliteDriver: SqlDriver
    get() = AndroidSqliteDriver(KMMTDB.Schema, Android.application.application, "KMMTB.db")