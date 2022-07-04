package com.kmmt.common.expectations

import kotlinx.coroutines.CoroutineDispatcher

expect val DispatcherMain: CoroutineDispatcher

expect val DispatcherDefault: CoroutineDispatcher