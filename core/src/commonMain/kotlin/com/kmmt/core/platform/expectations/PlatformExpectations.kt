package com.kmmt.core.platform.expectations

import com.kmmt.core.models.BundleExtras
import com.kmmt.core.platform.Platform
import org.koin.core.module.Module


expect fun getAppContextAsKoinBean(appContext: Any): Module

expect class BundleX(extras: BundleExtras)

expect val platform:Platform
