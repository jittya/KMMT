package com.jittyandiyan.shared.demo.features.kampkit

import com.jittyandiyan.mobile.TBreed
import com.kmmt.core.architecture.presenter.BasePresenter
import com.kmmt.core.functional.Either
import com.kmmt.core.liveData.LiveDataObservable
import com.kmmt.core.models.Failure
import com.kmmt.core.platform.runOnAndroid
import com.jittyandiyan.shared.demo.dataSources.cache.BreedListCache
import com.jittyandiyan.shared.demo.dataSources.localDB.BreedTableHelper

class BreedPresenter(view: BreedView) : BasePresenter<BreedView>(view) {

    private lateinit var breedTableHelper: BreedTableHelper
    private lateinit var breedLiveDataObservable: LiveDataObservable<Either<List<TBreed>, Failure>>
    private lateinit var breedListCache: BreedListCache

    override fun onStartPresenter() {

        runOnAndroid {
            getView()?.setPageTitle("Breed List")
        }

        breedTableHelper = BreedTableHelper()
        breedListCache = BreedListCache(getBackgroundCoroutineScope())

        breedLiveDataObservable = observe { breedList ->
            breedList.either({
                getView()?.showPopUpMessage(it.message)
                getView()?.stopRefreshing()
            }, {
                getView()?.refreshBreedList(it)
                getView()?.stopRefreshing()
            })

        }

        refreshBreedListCache(forceRefresh = false)

        observeBreedsTable()

        getView()?.setBreedRefreshAction(this::refreshBreedListCache)
        getView()?.setBreedFavouriteClickAction(this::invertBreedFavouriteState)
    }

    private fun refreshBreedListCache(forceRefresh: Boolean) {
        breedListCache.cacheData(Unit, forceRefresh)
        { cachedResult ->
            cachedResult.either({ failure ->
                breedLiveDataObservable.setValue(Either.Failure(failure))
            }, { success ->
                println("Cache updated : $success")
            })
        }
    }

    private fun invertBreedFavouriteState(tBreed: TBreed) {
        runOnBackground {
            breedTableHelper.updateFavorite(tBreed.id, tBreed.favorite.not())
        }
    }

    private fun observeBreedsTable() {
        //get Data from db with observe (Flow)
        runOnBackground {
            breedTableHelper.getAllBreeds().collect {
                println("data from DB : Size = ${it.size}")
                breedLiveDataObservable.setValue(Either.Success(it))
            }
        }
    }
}

