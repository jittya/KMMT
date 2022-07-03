package com.kmmt.persistance.expectations

import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module

expect val sqlDriverModule: Module

expect val Dispatchers_Default: CoroutineDispatcher