package com.jittyandiyan.shared.demoTVMazeShowSearch.features.tvshows

import com.kmmt.core.architecture.view.BaseView
import com.kmmt.models.demotvshowsearch.domain.TVShowInfo
import kotlin.reflect.KFunction1

interface TVShowsSearchView:BaseView {
    fun showTVShowsList(tvShowList: List<TVShowInfo>)

    fun setSearchQueryChangeListener(onSearchQueryStringChanged: KFunction1<String, Unit>)
}