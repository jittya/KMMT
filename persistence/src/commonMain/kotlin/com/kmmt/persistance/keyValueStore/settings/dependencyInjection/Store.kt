package com.kmmt.persistance.keyValueStore.settings.dependencyInjection

import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Store : KoinComponent {
        val settings: Settings by inject ()
}