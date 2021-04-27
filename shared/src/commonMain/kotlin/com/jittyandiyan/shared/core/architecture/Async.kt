package com.jittyandiyan.shared.core.architecture

import com.jittyandiyan.shared.core.expectations.ApplicationDispatcher
import com.jittyandiyan.shared.core.expectations.Dispatchers_Default
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlin.reflect.KSuspendFunction0
import kotlin.reflect.KSuspendFunction1

abstract class Async {

    private val backgroundCoroutineScope = CoroutineScope(Dispatchers_Default)
    private val uiCoroutineScope = CoroutineScope(ApplicationDispatcher)

    protected fun <OUT> Flow<OUT>.resultOnUI(function: (OUT) -> Unit) {
        uiCoroutineScope.launch {
            collect {
                function.invoke(it)
            }
        }
    }

    fun cancelAllRunningCoroutines(reason:String)
    {
        backgroundCoroutineScope.coroutineContext.cancelChildren(CancellationException(reason))
    }

    fun <OUT> runOnBackground(function: () -> KSuspendFunction0<OUT>): Flow<OUT> = channelFlow {
        var job = backgroundCoroutineScope.async<OUT> {
            return@async function.invoke().invoke()
        }
        send(job.await())
    }

    fun <IN,OUT> runOnBackground(param: IN, function: () -> KSuspendFunction1<IN, OUT>): Flow<OUT> = channelFlow {
        var job = backgroundCoroutineScope.async<OUT> {
            return@async function.invoke().invoke(param)
        }
        send(job.await())
    }

}