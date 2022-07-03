package com.kmmt.core.liveData

import com.kmmt.core.liveData.lifecycle.LiveDataLifecycle
import com.kmmt.core.liveData.lifecycle.LiveDataLifecycleAndObserver

actual open class LiveDataX<T> {

    private val foreverObservers = mutableListOf<(T) -> Unit>()
    private val foreverLiveDataObservers = mutableMapOf<LiveDataX<*>, MutableList<(T) -> Unit>>()
    private val lifecycleObservers = mutableMapOf<LiveDataLifecycle, LiveDataLifecycleAndObserver<T>>()

    internal var _value : T? = null
        set(value) {
            field = value
            notifyObservers()
        }

    actual open val value: T?
        get() = _value

    fun removeObserveForever(block: (T) -> Unit){
        foreverObservers.remove(block)
    }

    actual fun observeForever(block: (T) -> Unit) {
        foreverObservers.add(block)

        value?.let {
            block(it)
        }
    }

    internal fun addLiveDataObserver(liveDataObserver: LiveDataX<*>, block: (T) -> Unit) {
        var listObservers = foreverLiveDataObservers.get(liveDataObserver)
        if(listObservers == null){
            foreverLiveDataObservers[liveDataObserver] = mutableListOf(block)
        } else {
            listObservers.add(block)
        }

        foreverObservers.add(block)

        value?.let {
            block(it)
        }
    }
    internal fun removeLiveDataObserver(liveDataObserver: LiveDataX<*>) {
        var listObservers = foreverLiveDataObservers.get(liveDataObserver)
        if(listObservers != null){
            listObservers.forEach{
                foreverObservers.remove(it)
            }
            listObservers.clear()
            foreverLiveDataObservers.remove(liveDataObserver)
        }
    }

    actual fun hasObservers(): Boolean {
        return !foreverObservers.isEmpty() && !lifecycleObservers.isEmpty()
    }

    actual fun observe(lifecycle: LiveDataLifecycle, block: (T) -> Unit) {
        this.addObserver(lifecycle, block)
    }

    internal fun notifyObservers(){
        value?.let { value ->
            foreverObservers.forEach {
                it(value)
            }
            lifecycleObservers.values.forEach {
                it.observers.forEach {
                    it(value)
                }
            }
        }
    }

    internal fun addObserver(lifecycle: LiveDataLifecycle, block: (T) -> Unit){
        var lifecycleAndObserver = this.lifecycleObservers[lifecycle]
        if(lifecycleAndObserver == null){
            lifecycleAndObserver = LiveDataLifecycleAndObserver(lifecycle)

            lifecycle.addStopObserver {
                lifecycleObservers.remove(lifecycle)
            }
            this.lifecycleObservers[lifecycle] = lifecycleAndObserver
        }
        lifecycleAndObserver.observers.add(block)

        value?.let {
            block(it)
        }
    }
}

actual open class MutableLiveDataX<T> : LiveDataX<T>() {
    actual override var value: T?
        get() = _value
        set(value) {
            _value = value
        }
}

actual open class MediatorLiveDataX<T> : MutableLiveDataX<T>() {
    actual fun <S> addSource(other: LiveDataX<S>, block: ((S) -> Unit)) {
        other.addLiveDataObserver(this, block)
    }

    actual fun removeSource(other: LiveDataX<*>) {
        other.removeLiveDataObserver(this)
    }
}