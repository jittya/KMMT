package com.jittyandiyan.shared.demoTVMazeShowSearch.features.tvshows

import com.kmmt.domain.demotvshowsearch.repository.TVShowSearchRepositoryTVMazeAPI
import com.kmmt.core.architecture.presenter.BasePresenter
import com.kmmt.domain.demotvshowsearch.usecase.TVShowSearchRepoUseCase
import com.kmmt.resources.Resources
import com.kmmt.resources.expectations.localized

class TVShowsSearchPresenter(
    view: TVShowsSearchView,
    private val tvShowSearchUseCase: TVShowSearchRepoUseCase = TVShowSearchRepoUseCase(
        TVShowSearchRepositoryTVMazeAPI()
    )
) : BasePresenter<TVShowsSearchView>(view, tvShowSearchUseCase) {


    override fun onStartPresenter() {
        getView()?.setPageTitle(Resources.strings.tvShows.localized())
        getView()?.setSearchQueryChangeListener(this::searchTVShows)
    }

    private fun searchTVShows(query: String) {
        if (query.trim().isNotEmpty()) {

//region simple way to execute code in background thread


//            runOnBackgroundWithResult {
//                TVMazeAPI().getTVShows(query)
//            }.resultOnUI { tvShowSearchResult ->
//                tvShowSearchResult.either({
//                    getView()?.showPopUpMessage(it.message)
//                }, {
//                    getView()?.showTVShowsList(it)
//                })
//            }


//endregion

            // To avoid multiple background calls using use-case methodology
            tvShowSearchUseCase(query).resultOnUI { tvShowSearchResult ->
                tvShowSearchResult.either({
                    getView()?.showPopUpMessage(it.message)
                }, {
                    getView()?.showTVShowsList(it)
                })
            }


        } else {
            tvShowSearchUseCase.cancelPendingTasks()
            getView()?.showTVShowsList(emptyList())
        }
    }

}