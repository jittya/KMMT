package com.jittyandiyan.shared.core.platform.expectations

import com.jittyandiyan.shared.core.models.BundleExtras
import com.jittyandiyan.shared.core.platform.Platform
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module

internal expect val ApplicationDispatcher: CoroutineDispatcher

internal expect val Dispatchers_Default: CoroutineDispatcher

expect fun getAppContextAsKoinBean(appContext: Any): Module

expect val sqlDriverModule: Module

expect class BundleX(extras: BundleExtras)

expect val platform:Platform

expect val keyValueStore: Settings