package com.kmmt.common.dependencyInjection

import com.kmmt.common.expectations.Application
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object IOS : KoinComponent {
    val application: Application by inject ()
}