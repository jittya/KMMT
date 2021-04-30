package com.jittyandiyan.shared.core.platform.expectations

import android.content.Context
import android.os.Bundle
import com.jittyandiyan.mobile.KMMTDB
import com.jittyandiyan.shared.core.dependencyInjection.AndroidKoinComponents
import com.jittyandiyan.shared.core.models.BundleExtras
import com.jittyandiyan.shared.core.platform.Android
import com.jittyandiyan.shared.core.platform.Platform
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
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


actual class BundleX  {
     val bundle = Bundle()
    actual constructor(extras: BundleExtras)
    {
        extras.getKeys().forEach { key ->
            extras.getValue(key)?.let { value->
                if (value is String)
                {
                    bundle.putString(key,value)
                }
                else if (value is Boolean)
                {
                    bundle.putBoolean(key,value)
                }
                else if (value is Int)
                {
                    bundle.putInt(key,value)
                }
                else if (value is Long)
                {
                    bundle.putLong(key,value)
                }
                else if (value is Float)
                {
                    bundle.putFloat(key,value)
                }
                else if (value is Double)
                {
                    bundle.putDouble(key,value)
                }
            }
        }
    }
}

actual val platform:Platform = Android("Android",android.os.Build.VERSION.SDK_INT)

actual val keyValueStore: Settings
    get() = AndroidSettings(AndroidKoinComponents().androidContext.getSharedPreferences("KeyValueStore", Context.MODE_PRIVATE))
