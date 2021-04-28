package com.jittyandiyan.androidApp

import android.app.Application
import com.jittyandiyan.shared.core.KMMTApp

class KMMTApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KMMTApp.init(this)
    }
}