package com.kmmt.core.platform.expectations

import com.kmmt.core.models.BundleExtras
import com.kmmt.core.platform.Platform
import com.kmmt.core.platform.IOSPlatform
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIDevice
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext


actual fun getAppContextAsKoinBean(appContext: Any): Module {
    return module { }
}

actual class BundleX {
    var extras: BundleExtras

    actual constructor(extras: BundleExtras) {
        this.extras = extras
    }
}

actual val platform: Platform =
    IOSPlatform(UIDevice.currentDevice.systemName(), UIDevice.currentDevice.systemVersion.toDouble())
