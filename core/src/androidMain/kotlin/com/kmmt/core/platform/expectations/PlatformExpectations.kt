package com.kmmt.core.platform.expectations

import android.os.Bundle
import com.kmmt.core.bundle.BundleValues


actual class BundleParcel(val bundle: Bundle) {
    actual constructor(extras: BundleValues) : this(Bundle()) {
        extras.getKeys().forEach { key ->
            extras.getValue(key)?.let { value ->
                when (value) {
                    is String -> {
                        bundle.putString(key, value)
                    }
                    is Boolean -> {
                        bundle.putBoolean(key, value)
                    }
                    is Int -> {
                        bundle.putInt(key, value)
                    }
                    is Long -> {
                        bundle.putLong(key, value)
                    }
                    is Float -> {
                        bundle.putFloat(key, value)
                    }
                    is Double -> {
                        bundle.putDouble(key, value)
                    }
                }
            }
        }
    }
}



