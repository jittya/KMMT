package com.jittyandiyan.shared.core.dependencyInjection

import android.content.Context
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AndroidKoinComponents : KoinComponent {
        val androidContext: Context by inject ()
}