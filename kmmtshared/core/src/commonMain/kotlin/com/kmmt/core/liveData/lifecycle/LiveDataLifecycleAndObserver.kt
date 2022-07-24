package com.kmmt.core.liveData.lifecycle

class LiveDataLifecycleAndObserver<T>(val lifecycle: LiveDataLifecycle){

    val observers = mutableListOf<(T) -> Unit>()

    init {
        lifecycle.addStopObserver {
            observers.clear()
        }
    }

}