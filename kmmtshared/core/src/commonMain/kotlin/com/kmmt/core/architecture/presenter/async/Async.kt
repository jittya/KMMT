package com.kmmt.core.architecture.presenter.async


import com.kmmt.common.expectations.DispatcherDefault
import com.kmmt.common.expectations.DispatcherMain
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class Async {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("CoroutineExceptionHandler : " + throwable.stackTraceToString())
        throw throwable
    }

    private val backgroundCoroutineScope = CoroutineScope(DispatcherDefault + exceptionHandler)
    private val uiCoroutineScope = CoroutineScope(DispatcherMain + exceptionHandler)


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

    fun <OUT> runOnBackgroundWithResult(function: suspend () -> OUT): Flow<OUT> = flow {
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