package com.kmmt.core.liveData

fun <X, Y> LiveDataX<X>.map(mapFunction: (X) -> Y): LiveDataX<Y> {
    return Transformations.map(this, mapFunction)
}

fun <X, Y> LiveDataX<X>.switchMap(switchMapFunction: (X) -> LiveDataX<Y>): LiveDataX<Y> {
    return Transformations.switchMap(this, switchMapFunction)
}

object Transformations {

    fun <X, Y> map(
        source: LiveDataX<X>,
        mapFunction: (X) -> Y): LiveDataX<Y> {
        val result = MediatorLiveDataX<Y>()
        result.addSource(source) { value ->
            result.value = mapFunction.invoke(value)
        }
        return result
    }

    fun <X, Y> switchMap(
        source: LiveDataX<X>,
        switchMapFunction: (X) -> LiveDataX<Y>): LiveDataX<Y> {
        val result = MediatorLiveDataX<Y>()

        var mSource: LiveDataX<Y>? = null

        result.addSource(source) { x ->
            val newLiveData = switchMapFunction.invoke(x)
            if (mSource === newLiveData) {
                return@addSource
            }
            mSource?.let {
                result.removeSource(it)
            }
            mSource = newLiveData
            mSource?.let {
                result.addSource(it) { y ->
                    result.value = y
                }
            }
        }
        return result
    }
}