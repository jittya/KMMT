package com.jittyandiyan.shared.demo.features.kampkit

import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import com.jittyandiyan.shared.core.liveData.LiveDataObservable

class BreedViewModel(view: BreedView) : BaseViewModel<BreedView>(view) {
    lateinit var booleanDataFlow: LiveDataObservable<Boolean>
    override fun onStartViewModel() {

        booleanDataFlow = LiveDataObservable(getLifeCycle())





    }

    override fun onDetached() {
        super.onDetached()
    }

}

