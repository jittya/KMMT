package com.kmmt.common.dependencyInjection

import android.content.Context
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Android : KoinComponent {
        val androidContext: Context by inject ()
}