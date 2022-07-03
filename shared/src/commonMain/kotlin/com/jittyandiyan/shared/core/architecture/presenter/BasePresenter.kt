package com.jittyandiyan.shared.core.architecture.presenter

import com.jittyandiyan.shared.core.architecture.usecase.BaseUseCase
import com.jittyandiyan.shared.core.architecture.view.BaseView
import com.jittyandiyan.shared.core.architecture.presenter.async.Async
import com.jittyandiyan.shared.core.architecture.presenter.viewState.ViewState
import com.jittyandiyan.shared.core.extensions.toObject
import com.jittyandiyan.shared.core.liveData.LiveDataObservable
import com.jittyandiyan.shared.core.liveData.lifecycle.LiveDataLifecycle
import com.jittyandiyan.shared.core.models.BundleExtras
import com.jittyandiyan.shared.core.platform.expectations.BundleX
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

    fun setBundle(bundle: BundleExtras) {
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
        bundle: BundleExtras.() -> Unit
    ): BundleX {
        return BundleX(BundleExtras().also(bundle))
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


