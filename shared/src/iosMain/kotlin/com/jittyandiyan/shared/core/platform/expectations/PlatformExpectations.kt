package com.jittyandiyan.shared.core.platform.expectations

import com.jittyandiyan.mobile.KMMTDB
import com.jittyandiyan.shared.core.models.BundleExtras
import com.jittyandiyan.shared.core.platform.Platform
import com.jittyandiyan.shared.core.platform.iOS
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
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

internal actual val ApplicationDispatcher: CoroutineDispatcher = NsQueueDispatcher(dispatch_get_main_queue())

internal class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        try {
            dispatch_async(dispatchQueue) {
                try {
                    block.run()
                } catch (e: Exception) {
                    print("ApplicationDispatcher Exception in ios block.run() " + e.message)
                }

            }
        } catch (e: Exception) {
            print("ApplicationDispatcher Exception in ios " + e.message)
        }
    }
}

actual val Dispatchers_Default: CoroutineDispatcher = Dispatchers.Main

actual fun getAppContextAsKoinBean(appContext: Any): Module {
    return module { }
}

@Suppress("UNRESOLVED_REFERENCE", "TYPE_MISMATCH")
actual val sqlDriverModule: Module
    get() = module { single<SqlDriver> { NativeSqliteDriver(KMMTDB.Schema, "KMMTB.db") } }

actual class BundleX {
    var extras: BundleExtras

    actual constructor(extras: BundleExtras) {
        this.extras = extras
    }
}

actual val platform: Platform =
    iOS(UIDevice.currentDevice.systemName(), UIDevice.currentDevice.systemVersion.toDouble())

@Suppress("UNRESOLVED_REFERENCE")
actual val keyValueStore: Settings
    get() = AppleSettings(NSUserDefaults.standardUserDefaults())