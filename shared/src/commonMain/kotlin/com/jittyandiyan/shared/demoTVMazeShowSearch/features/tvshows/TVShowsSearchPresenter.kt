package com.jittyandiyan.shared.demoTVMazeShowSearch.features.tvshows

import com.jittyandiyan.shared.demoTVMazeShowSearch.dataSources.repository.TVShowSearchRepositoryTVMazeAPI
import com.kmmt.core.architecture.presenter.BasePresenter

class TVShowsSearchPresenter(
    view: TVShowsSearchView,
    private val tvShowSearchUseCase: TVShowSearchRepoUseCase = TVShowSearchRepoUseCase(
        TVShowSearchRepositoryTVMazeAPI()
    )
) : BasePresenter<TVShowsSearchView>(view, tvShowSearchUseCase) {


    override fun onStartPresenter() {
        getView()?.setPageTitle("TV Shows")
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