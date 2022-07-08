package com.kmmt.core.architecture.presenter

import com.kmmt.common.extensions.toObject
import com.kmmt.core.architecture.usecase.BaseUseCase
import com.kmmt.core.architecture.view.BaseView
import com.kmmt.core.architecture.presenter.async.Async
import com.kmmt.core.architecture.presenter.viewState.ViewState
import com.kmmt.core.liveData.LiveDataObservable
import com.kmmt.core.liveData.lifecycle.LiveDataLifecycle
import com.kmmt.core.bundle.BundleValues
import com.kmmt.core.platform.expectations.BundleParcel
import kotlin.collections.set

abstract class BasePresenter<View>(private var view: View, vararg useCases:BaseUseCase<*,*>) : Async() where View : BaseView {

    private lateinit var lifeCycle: LiveDataLifecycle
    private var viewState = ViewState.UNKNOWN

    init {
        viewState = ViewState.INITIALIZED
        useCases.forEach { it.setAsync(this) }
    }

    fun getLifeCycle(): LiveDataLifecycle {
        return lifeCycle
    }

    abstract fun onStartPresenter()

    open fun onInit() {
        if (this::lifeCycle.isInitialized)
            lifeCycle.start()
        if (viewState == ViewState.INITIALIZED) {
            viewState = ViewState.STARTED
            onStartPresenter()
        } else {
            viewState = ViewState.STARTED
        }
    }

    fun onDetached() {
        viewState = ViewState.DETACHED
        cancelAllRunningCoroutines("Presenter onDetached called")
        if (this::lifeCycle.isInitialized)
        lifeCycle.stop()
    }

    fun getView(): View? {
        return if (viewState == ViewState.STARTED) {
            view
        } else {
            null
        }
    }

    fun getPresenter(): BasePresenter<BaseView> {
        @Suppress("UNCHECKED_CAST")
        return this as BasePresenter<BaseView>
    }

    var bundlesValues = mutableMapOf<String, Any?>()

    fun setBundle(bundle: BundleValues) {
        bundlesValues = bundle.extras
    }

    fun setBundleValue(paramName: String, paramValue: Any?) {
        bundlesValues[paramName] = paramValue
    }

    inline fun <reified T> getBundleValue(paramName: String): T? where T : Any? {
        if (bundlesValues.containsKey(paramName)) {
            var value = bundlesValues[paramName]
            if (value != null) {
                if (value is String) {
                    return value.toObject<T>()
                } else {
                    return value as T
                }
            } else {
                return null
            }
        } else {
            return null
        }
    }

    fun Bundle(
        bundle: BundleValues.() -> Unit
    ): BundleParcel {
        return BundleParcel(BundleValues().also(bundle))
    }

    fun setLifeCycle(lifeCycle: LiveDataLifecycle) {
        this.lifeCycle = lifeCycle
    }

    fun <T> observe(result: (T) -> Unit): LiveDataObservable<T> {
        var liveData = LiveDataObservable<T>(getLifeCycle())
        liveData.observe(result)
        return liveData
    }
}


