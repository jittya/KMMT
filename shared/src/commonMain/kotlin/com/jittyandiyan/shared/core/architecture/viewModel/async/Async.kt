package com.jittyandiyan.shared.core.architecture.viewModel.async

import com.jittyandiyan.mobile.KMMTDB
import com.jittyandiyan.shared.core.platform.expectations.ApplicationDispatcher
import com.jittyandiyan.shared.core.platform.expectations.Dispatchers_Default
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

abstract class Async: KoinComponent {

    private val backgroundCoroutineScope = CoroutineScope(Dispatchers_Default)
    private val uiCoroutineScope = CoroutineScope(ApplicationDispatcher)

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

    protected fun <OUT> Flow<OUT>.resultAsync(function: (OUT) -> Unit) {
        backgroundCoroutineScope.launch {
            collect {
                function.invoke(it)
            }
        }
    }

     fun <OUT> Flow<OUT>.cacheOnDB(
        db: KMMTDB.(OUT) -> Unit,
        function: (Flow<OUT>) -> Unit= { }
    ) {
        backgroundCoroutineScope.launch {
            collect {
                db.invoke(get(),it)
                function.invoke(flow {
                    emit(it)
                })
            }
        }
    }

    fun cancelAllRunningCoroutines(reason:String)
    {
        backgroundCoroutineScope.coroutineContext.cancelChildren(CancellationException(reason))
    }

    fun <OUT> runOnBackground(function: suspend () -> OUT): Flow<OUT> = flow {
        val job = backgroundCoroutineScope.async {
            return@async function.invoke()
        }
        emit(job.await())
    }

}