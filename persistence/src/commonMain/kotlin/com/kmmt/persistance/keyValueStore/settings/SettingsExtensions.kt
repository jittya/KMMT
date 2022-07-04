package com.kmmt.persistance.keyValueStore.settings

import com.kmmt.common.extensions.toJsonString
import com.kmmt.common.extensions.toObject
import com.russhwolf.settings.Settings
import kotlinx.serialization.KSerializer


fun <T> Settings.putSerializable(key: String, value: T, serializer: KSerializer<T>) {
    this.putString(key, value.toJsonString(serializer))
}

fun <T> Settings.getSerializable(key: String, serializer: KSerializer<T>): T? {
    return getStringOrNull(key)?.toObject(serializer)
}