package com.jittyandiyan.shared.demoTVMazeShowSearch.features.tvshows

import com.jittyandiyan.shared.core.architecture.view.BaseView
import com.jittyandiyan.shared.demoTVMazeShowSearch.models.TVShowInfo
import kotlin.reflect.KFunction1

interface TVShowsSearchView:BaseView {
    fun showTVShowsList(tvShowList: List<TVShowInfo>)

    fun setSearchQueryChangeListener(onSearchQueryStringChanged: KFunction1<String, Unit>)
}