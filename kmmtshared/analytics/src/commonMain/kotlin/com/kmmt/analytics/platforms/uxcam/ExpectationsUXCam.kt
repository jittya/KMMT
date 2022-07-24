package com.kmmt.analytics.platforms.uxcam

import org.koin.core.module.KoinDefinition
import org.koin.core.module.Module


expect fun analyticsUXCamModule(module: Module, key: String): KoinDefinition<AnalyticsUXCam>

