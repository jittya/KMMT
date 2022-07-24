package com.kmmt.core.dataFlow


sealed class FlowState<out T> {
    data class Success<T>(val data: T) : FlowState<T>()
    data class Error(val exception: String) : FlowState<Nothing>()
    object Empty : FlowState<Nothing>()
    object Loading : FlowState<Nothing>()
}