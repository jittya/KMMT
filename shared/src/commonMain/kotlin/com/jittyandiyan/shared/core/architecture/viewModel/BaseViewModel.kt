package com.jittyandiyan.shared.core.architecture.viewModel

import com.jittyandiyan.shared.core.architecture.view.BaseView
import com.jittyandiyan.shared.core.architecture.viewModel.async.Async
import com.jittyandiyan.shared.core.architecture.viewModel.viewState.ViewState
import com.jittyandiyan.shared.core.expectations.Bundle
import com.jittyandiyan.shared.core.extensions.toObject
import com.jittyandiyan.shared.core.models.BundleCommon

abstract class BaseViewModel<View>(private var view: View) : Async() where View : BaseView {

    private var viewState = ViewState.UNKNOWN

    init {
        viewState = ViewState.INITIALIZED
    }

    abstract fun onStartViewModel()

    open fun onInit() {
        if (viewState == ViewState.INITIALIZED) {
            viewState = ViewState.STARTED
            onStartViewModel()
        }
    }

    fun onDetached() {
        viewState = ViewState.DETACHED
        cancelAllRunningCoroutines("ViewModel onDetached called")
    }

    fun getView(): View? {
        return if (viewState == ViewState.STARTED) {
            view
        } else {
            null
        }
    }

    fun getViewModel(): BaseViewModel<BaseView> {
        @Suppress("UNCHECKED_CAST")
        return this as BaseViewModel<BaseView>
    }

    var bundlesValues = mutableMapOf<String, Any?>()

    fun setBundle(bundle:BundleCommon)
    {
        bundlesValues=bundle.extras
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

    fun bundle(
        bundle: BundleCommon.() -> Unit
    ): Bundle {
        return Bundle(BundleCommon().also(bundle))
    }
}


