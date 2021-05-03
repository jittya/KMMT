package com.jittyandiyan.shared.demo.features.kampkit

import com.jittyandiyan.mobile.TBreed
import com.jittyandiyan.shared.core.architecture.view.BaseView
import kotlin.reflect.KFunction1

interface BreedView:BaseView {
    fun refreshBreedList(breedList: List<TBreed>)
    fun setBreedRefreshAction(refresh: KFunction1<Boolean, Unit>)
    fun stopRefreshing()
    fun setBreedFavouriteClickAction(invertBreedFavouriteState: KFunction1<TBreed, Unit>)
}