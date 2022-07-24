package com.kmmt.analytics.platforms.mixpanel

import org.koin.core.module.KoinDefinition
import org.koin.core.module.Module


expect fun analyticsMixpanelModule(module: Module, key: String): KoinDefinition<AnalyticsMixpanel>

