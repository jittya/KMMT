package com.kmmt.core.liveData.lifecycle

class LiveDataLifecycle {

    private val lifecycleStopObservers = mutableListOf<() -> Unit>()
    val tags = mutableMapOf<String, Any>()

    var isStarted : Boolean = false
        get
        private set

    fun start(){
        isStarted = true
    }
    fun stop() {
        isStarted = false

        notifyObserversStop()
    }

    fun addStopObserver(block: () -> Unit){
        lifecycleStopObservers.add(block)
    }

    fun notifyObserversStop(){
        lifecycleStopObservers.forEach {
            it()
        }
        lifecycleStopObservers.clear()
    }
}

