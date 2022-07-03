package com.kmmt.core.platform.expectations

import com.kmmt.core.models.BundleExtras
import com.kmmt.core.platform.Platform
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module

expect val ApplicationDispatcher: CoroutineDispatcher

expect val Dispatchers_Default: CoroutineDispatcher

expect fun getAppContextAsKoinBean(appContext: Any): Module

expect class BundleX(extras: BundleExtras)

expect val platform:Platform

expect val keyValueStore: Settings