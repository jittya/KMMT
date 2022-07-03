package com.jittyandiyan.androidApp

import android.app.Application
import com.kmmt.core.KMMTApp

class KMMTApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        com.kmmt.core.KMMTApp.init(this)
    }
}