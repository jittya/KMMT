package com.jittyandiyan.shared.core.architecture.viewModel.async

import com.jittyandiyan.shared.core.platform.expectations.ApplicationDispatcher
import com.jittyandiyan.shared.core.platform.expectations.Dispatchers_Default
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

abstract class Async : KoinComponent {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("CoroutineExceptionHandler : " + throwable.stackTraceToString())
        throw throwable
    }

    private val backgroundCoroutineScope = CoroutineScope(Dispatchers_Default + exceptionHandler)
    private val uiCoroutineScope = CoroutineScope(ApplicationDispatcher + exceptionHandler)


    fun getBackgroundCoroutineScope(): CoroutineScope {
        return backgroundCoroutineScope
    }

    fun getUICoroutineScope(): CoroutineScope {
        return uiCoroutineScope
    }

    protected fun <OUT> Flow<OUT>.resultOnUI(function: (OUT) -> Unit) {
        uiCoroutineScope.launch {
            collect {
                function.invoke(it)
            }
        }
    }

    protected fun <OUT> Flow<OUT>.resultOnBackground(function: suspend (OUT) -> Unit) {
        backgroundCoroutineScope.launch {
            collect {
                function.invoke(it)
            }
        }
    }

    fun cancelAllRunningCoroutines(reason: String) {
        backgroundCoroutineScope.coroutineContext.cancelChildren(CancellationException(reason))
    }

    fun <OUT> runOnBackgroundAsFlow(function: suspend () -> OUT): Flow<OUT> = flow {
        val result = withContext(backgroundCoroutineScope.coroutineContext) {
            return@withContext function.invoke()
        }
        emit(result)
    }

    fun runOnBackground(function: suspend () -> Unit) {
        backgroundCoroutineScope.launch {
            function.invoke()
        }
    }

    fun runOnUI(function: suspend () -> Unit) {
        uiCoroutineScope.launch {
            function.invoke()
        }
    }

}