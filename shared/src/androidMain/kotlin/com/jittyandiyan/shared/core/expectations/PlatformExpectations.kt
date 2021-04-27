package com.jittyandiyan.shared.core.expectations

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Main

internal actual val Dispatchers_Default: CoroutineDispatcher=Dispatchers.Default
