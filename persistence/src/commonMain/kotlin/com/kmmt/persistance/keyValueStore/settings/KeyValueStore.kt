package com.kmmt.persistance.keyValueStore.settings


import com.kmmt.persistance.keyValueStore.settings.dependencyInjection.Store
import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains
import com.russhwolf.settings.get
import kotlinx.serialization.KSerializer

fun storeValue(
    keyValueStore: Settings.() -> Unit
) {
    keyValueStore.invoke(Store.settings)
}

inline fun <reified T> getStoreValue(key:String):T?
{
    try {
        return Store.settings[key]
    } catch (e: IllegalArgumentException) {
        if (Store.settings.contains(key)) {
            throw IllegalArgumentException("KSerializer expected!. Use getStoreValue(key,KSerializer<T>)")
        }
        throw e
    }
}

inline fun <reified T> getStoreValue(key: String, serializer: KSerializer<T>):T?
{
    return Store.settings.getSerializable(key, serializer)
}