package com.kmmt.core

import com.kmmt.core.dependencyInjection.initKoin
import org.koin.core.KoinApplication
import kotlin.native.concurrent.ThreadLocal

class KMMTApp {
    @ThreadLocal
    companion object {
        lateinit var koinApp: KoinApplication
        fun init(context: Any) {
            koinApp = initKoin(context)
        }
    }
}