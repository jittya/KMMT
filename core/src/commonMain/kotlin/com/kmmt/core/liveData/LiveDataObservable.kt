package com.kmmt.core.liveData

import com.kmmt.core.liveData.lifecycle.LiveDataLifecycle

open class LiveDataObservable<T>(private val lifeCycle:LiveDataLifecycle) {
    private var kMediatorLiveData = MediatorLiveDataX<T>()

    fun setValue(value: T) {
        kMediatorLiveData.value = value
    }

    fun addSource(liveData: LiveDataX<T>) {
        kMediatorLiveData.addSource(liveData) {
            kMediatorLiveData.value=it
        }
    }

    fun <T1>addSource(liveData: LiveDataX<T1>, converter: ((T1) -> T)) {
        kMediatorLiveData.addSource(liveData){
            kMediatorLiveData.value=converter.invoke(it)
        }
    }

    fun observe(result: (T) -> Unit) {
        kMediatorLiveData.observe(lifeCycle) {
            result.invoke(it)
        }
    }

}