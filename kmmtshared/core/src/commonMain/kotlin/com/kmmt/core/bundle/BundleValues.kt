package com.kmmt.core.bundle

import com.kmmt.common.extensions.toJsonString
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

class BundleValues {
     val extras = mutableMapOf<String, Any?>()

    fun getKeys(): Set<String> {
        return extras.keys.toSet()
    }

    fun getValue(key: String): Any? {
        return extras[key]
    }

    private fun put(name: String, value: Any) {
        extras.put(name, value)
    }

    fun putBooleanExtra(name: String, value: Boolean) {
        put(name, value)
    }

    fun putBooleanListExtra(name: String, value: List<Boolean>) {
        put(name, value.toJsonString(ListSerializer(Boolean.serializer())))
    }

    fun putStringExtra(name: String, value: String) {
        put(name, value)
    }

    fun putStringListExtra(name: String, value: List<String>) {
        put(name, value.toJsonString(ListSerializer(String.serializer())))
    }

    fun putIntExtra(name: String, value: Int) {
        put(name, value)
    }

    fun putIntListExtra(name: String, value: List<Int>) {
        put(name, value.toJsonString(ListSerializer(Int.serializer())))
    }

    fun putLongExtra(name: String, value: Long) {
        put(name, value)
    }

    fun putLongListExtra(name: String, value: List<Long>) {
        put(name, value.toJsonString(ListSerializer(Long.serializer())))
    }

    fun putFloatExtra(name: String, value: Float) {
        put(name, value)
    }

    fun putFloatListExtra(name: String, value: List<Float>) {
        put(name, value.toJsonString(ListSerializer(Float.serializer())))
    }

    fun putDoubleExtra(name: String, value: Double) {
        put(name, value)
    }

    fun putDoubleListExtra(name: String, value: List<Double>) {
        put(name, value.toJsonString(ListSerializer(Double.serializer())))
    }

    fun <T> putSerializableExtra(name: String, value: T, serializer: KSerializer<T>) {
        put(name, value.toJsonString(serializer))
    }
}
