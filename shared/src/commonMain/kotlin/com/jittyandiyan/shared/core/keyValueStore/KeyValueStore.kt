package com.jittyandiyan.shared.core.keyValueStore

import com.jittyandiyan.shared.core.KMMTApp.Companion.koinApp
import com.jittyandiyan.shared.core.extensions.getSerializable
import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains
import com.russhwolf.settings.get
import kotlinx.serialization.KSerializer

fun storeValue(
    keyValueStore: Settings.() -> Unit
) {
    keyValueStore.invoke(koinApp.koin.get())
}

inline fun <reified T> getStoreValue(key:String):T?
{
    try {
        var value:T?= koinApp.koin.get<Settings>().get(key)
        return value
    }catch (e:IllegalArgumentException)
    {
        if (koinApp.koin.get<Settings>().contains(key))
        {
            throw IllegalArgumentException("KSerializer expected!. Use getStoreValue(key,KSerializer<T>)")
        }
        throw e
    }
}

inline fun <reified T> getStoreValue(key: String, serializer: KSerializer<T>):T?
{
    return koinApp.koin.get<Settings>().getSerializable(key, serializer)
}