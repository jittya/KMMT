package com.kmmt.core.platform.expectations

import android.content.Context
import android.os.Bundle
import com.kmmt.core.dependencyInjection.AndroidKoinComponents
import com.kmmt.core.models.BundleExtras
import com.kmmt.core.platform.Android
import com.kmmt.core.platform.Platform
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

 actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Main

 actual val Dispatchers_Default: CoroutineDispatcher=Dispatchers.Default

actual fun getAppContextAsKoinBean(appContext: Any): Module {
    appContext as Context
    return module {
        single<Context> { appContext }
    }
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
