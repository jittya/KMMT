package com.jittyandiyan.shared.demo.features.kampkit

import com.jittyandiyan.mobile.TBreed
import com.jittyandiyan.shared.core.architecture.view.BaseView

interface BreedView:BaseView {
    fun refreshBreedList(breedList: List<TBreed>)
}