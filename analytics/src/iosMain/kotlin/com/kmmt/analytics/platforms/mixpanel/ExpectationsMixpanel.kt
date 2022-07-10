package com.kmmt.analytics.platforms.mixpanel

import com.kmmt.analytics.expectations.AnalyticsContext
import org.koin.core.module.KoinDefinition
import org.koin.core.module.Module


actual fun analyticsMixpanelModule(module: Module): KoinDefinition<AnalyticsMixpanel>
{
    return module.single { AnalyticsMixpanel(
        AnalyticsContext(
        "KEY"
    )
    ) }
}