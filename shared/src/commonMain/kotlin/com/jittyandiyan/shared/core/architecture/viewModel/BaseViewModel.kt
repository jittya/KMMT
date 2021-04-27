package com.jittyandiyan.shared.core.architecture.viewModel

import com.jittyandiyan.shared.core.architecture.view.BaseView
import com.jittyandiyan.shared.core.architecture.viewModel.async.Async
import com.jittyandiyan.shared.core.architecture.viewModel.viewState.ViewState

abstract class BaseViewModel<View>(private var view: View): Async() where View : BaseView {

    private var viewState = ViewState.INITIALIZED

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
        if (viewState == ViewState.STARTED) {
            return view
        } else {
            return null
        }
    }

    fun getViewModel():BaseViewModel<BaseView>
    {
        return this as BaseViewModel<BaseView>
    }
}
