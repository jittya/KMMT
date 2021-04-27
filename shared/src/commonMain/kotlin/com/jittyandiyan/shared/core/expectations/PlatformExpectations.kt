package com.jittyandiyan.shared.core.expectations

import kotlinx.coroutines.CoroutineDispatcher

internal expect val ApplicationDispatcher: CoroutineDispatcher

internal expect val Dispatchers_Default: CoroutineDispatcher
