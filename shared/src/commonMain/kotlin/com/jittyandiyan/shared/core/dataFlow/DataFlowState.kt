package com.jittyandiyan.shared.core.dataFlow

import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

class DataFlowState<T>(val viewModel: BaseViewModel<*>, val initValue: T) {
    var _stateFlow: MutableStateFlow<FlowState<T>> = MutableStateFlow(FlowState.Loading)
    val stateFlow: StateFlow<FlowState<T>> = _stateFlow

    fun startFlow(vararg dataFlows: Flow<FlowState<T>>, result: (FlowState<T>) -> Unit) {
        collect(result)
        viewModel.getBackgroundCoroutineScope().launch {
            flowOf(elements = dataFlows).flattenConcat().collect { data ->
                _stateFlow.value = data
            }
        }
    }

    inline fun <reified IN, reified T1> startFlow(
        vararg pair: Pair<KSuspendFunction1<IN, T1>, IN>,
        noinline result: (FlowState<T>) -> Unit = {}
    ) {
        collect(result)
        viewModel.getBackgroundCoroutineScope().launch {
            flowOf(elements = pair.map { it.first.invoke(it.second) }.toTypedArray<T1>()).conflate().collect { data ->
                if (data is T1) {
                    try {
                        _stateFlow.value = FlowState.Success(data as T)
                    } catch (e: Exception) {
                        throw IllegalArgumentException("Invalid value type! T1")
                    }
                }
            }
        }
    }

    fun collect(function: (FlowState<T>) -> Unit) {
        viewModel.getUICoroutineScope().launch {
            stateFlow.collect {
                function.invoke(it)
            }
        }
    }

}