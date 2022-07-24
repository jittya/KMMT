package com.kmmt.common.expectations

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val DispatcherMain: CoroutineDispatcher = Dispatchers.Main

actual val DispatcherDefault: CoroutineDispatcher=Dispatchers.Default