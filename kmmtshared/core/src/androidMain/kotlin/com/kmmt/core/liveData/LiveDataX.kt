package com.kmmt.core.liveData

import androidx.lifecycle.*
import com.kmmt.core.liveData.lifecycle.LiveDataLifecycle

private val lifeCycleOwnerTag = "LifecycleOwner"

actual open class LiveDataX<T> {

    internal val liveData = MediatorLiveData<T>()
    actual open val value: T?
        get() = liveData.value

    actual fun observeForever(block: (T) -> Unit) {
        liveData.observeForever {
            block(it)
        }
    }

    actual fun hasObservers(): Boolean {
        return liveData.hasObservers()
    }

    actual fun observe(lifecycle: LiveDataLifecycle, block: (T) -> Unit) {
        val realLifecycleOwner = lifecycle.tags[lifeCycleOwnerTag]
        if (realLifecycleOwner == null || realLifecycleOwner !is LifecycleOwner) {
            throw Exception("Please use LifecycleOwner.kLifecycle() to create your lifecycle, or KLiveData.observe(LifecycleOwner, (T) -> Unit) on Android Platform")
        } else {
            lifecycle.tags.remove(lifeCycleOwnerTag)
            observe(realLifecycleOwner, block)
        }
    }

}

actual open class MutableLiveDataX<T> : LiveDataX<T>() {
    actual override var value: T?
        get() = liveData.value
        set(value) {
            liveData.postValue(value)
        }
}

actual open class MediatorLiveDataX<T> : MutableLiveDataX<T>() {
    actual fun <S> addSource(other: LiveDataX<S>, block: ((S) -> Unit)) {
        liveData.addSource(other.liveData) {
            block(it)
        }
    }

    actual fun removeSource(other: LiveDataX<*>) {
        liveData.removeSource(other.liveData)
    }
}

fun <T> LiveDataX<T>.observe(lifecycle: LifecycleOwner, block: (T) -> Unit) {
    this.liveData.observe(lifecycle, Observer<T> {
        block(it)
    })
}

fun LifecycleOwner.kLifecycle(): LiveDataLifecycle {
    val kLifecycle = LiveDataLifecycle()
    kLifecycle.tags[lifeCycleOwnerTag] = this
    return kLifecycle
}

val <T> LiveDataX<T>.toLivedata: LiveData<T>
    get() = this.liveData

val <T> MutableLiveDataX<T>.toMutableLiveData: MutableLiveData<T>
    get() = this.liveData

val <T> MediatorLiveDataX<T>.toMediatorLivedata: MediatorLiveData<T>
    get() = this.liveData

val <T> LiveData<T>.toKLivedata: LiveDataX<T>
    get() = this.transformToKLiveData()

val <T> MutableLiveData<T>.toKMutableLiveData: MutableLiveDataX<T>
    get() = this.transformToKLiveData()

val <T> MediatorLiveData<T>.toKMediatorLivedata: MediatorLiveDataX<T>
    get() = this.transformToKLiveData()

private fun <X> LiveData<X>.transformToKLiveData(): MediatorLiveDataX<X> {
    val result = MediatorLiveDataX<X>()

    observeForever {
        result.value = it
    }

    return result
}