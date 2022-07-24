package com.kmmt.core.liveData

import com.kmmt.core.liveData.lifecycle.LiveDataLifecycle

expect open class LiveDataX<T>() {
    fun observeForever(block: (T) -> Unit)
    open val value : T?

    fun hasObservers() : Boolean

    fun observe(lifecycle: LiveDataLifecycle, block: (T) -> Unit)
}

expect open class MutableLiveDataX<T>() : LiveDataX<T> {
    override var value : T?
}

expect class MediatorLiveDataX<T>() : MutableLiveDataX<T> {
    fun <S> addSource(other: LiveDataX<S>, block: ((S) -> Unit))
    fun removeSource(other: LiveDataX<*>)
}