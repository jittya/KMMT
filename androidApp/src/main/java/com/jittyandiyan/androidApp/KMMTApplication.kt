package com.jittyandiyan.androidApp

import android.app.Application
import com.kmmt.injector.koin.Injector

class KMMTApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.initKoin(com.kmmt.common.expectations.Application(this))
    }
}