package com.jittyandiyan.shared.core

import com.jittyandiyan.shared.core.dependencyInjection.initKoin

class KMMTApp {
    companion object {
        fun init(context: Any) {
            initKoin(context)
        }
    }
}