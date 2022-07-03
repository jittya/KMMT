package com.kmmt.core.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public fun <T> flowOf(elements: List<T>): Flow<T> = flow {
    for (element in elements) {
        emit(element)
    }
}