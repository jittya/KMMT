package com.kmmt.analytics.platforms.uxcam

import com.kmmt.analytics.expectations.AnalyticsContext
import org.koin.core.module.KoinDefinition
import org.koin.core.module.Module


actual fun analyticsUXCamModule(module: Module): KoinDefinition<AnalyticsUXCam> {
    return module.single {
        AnalyticsUXCam(
            AnalyticsContext(
                "zq9bczqjyhwi4zd", get()
            )
        )
    }
}
