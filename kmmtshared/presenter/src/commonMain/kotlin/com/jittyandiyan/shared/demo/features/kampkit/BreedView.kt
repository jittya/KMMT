package com.jittyandiyan.shared.demo.features.kampkit

import com.kmmt.core.architecture.view.BaseView
import com.kmmt.models.demo.domain.Breed
import kotlin.reflect.KFunction1

interface BreedView:BaseView {
    fun refreshBreedList(breedList: List<Breed>)
    fun setBreedRefreshAction(refresh: KFunction1<Boolean, Unit>)
    fun stopRefreshing()
    fun setBreedFavouriteClickAction(invertBreedFavouriteState: KFunction1<Breed, Unit>)
}