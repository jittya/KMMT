package com.kmmt.analytics.platforms.mixpanel

import com.kmmt.analytics.platforms.mixpanel.AnalyticsMixpanel
import org.koin.core.module.KoinDefinition
import org.koin.core.module.Module


expect fun analyticsMixpanelModule(module: Module, key: String): KoinDefinition<AnalyticsMixpanel>

