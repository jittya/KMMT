package com.jittyandiyan.shared.demo.features.kampkit

import com.kmmt.core.architecture.presenter.BasePresenter
import com.kmmt.core.functional.Either
import com.kmmt.core.liveData.LiveDataObservable
import com.kmmt.core.models.Failure
import com.kmmt.core.platform.runOnAndroid
import com.jittyandiyan.shared.demo.dataSources.cache.BreedListCache
import com.kmmt.persistance.dataSources.breed.Breed
import com.kmmt.persistance.dataSources.breed.BreedDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BreedPresenter(view: BreedView, ) : BasePresenter<BreedView>(view),KoinComponent {

    private val breedDataSource: BreedDataSource by inject()
    private lateinit var breedLiveDataObservable: LiveDataObservable<Either<List<Breed>, Failure>>
    private lateinit var breedListCache: BreedListCache

    override fun onStartPresenter() {

        runOnAndroid {
            getView()?.setPageTitle("Breed List")
        }

        breedListCache = BreedListCache(getBackgroundCoroutineScope(),breedDataSource)

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

    private fun invertBreedFavouriteState(tBreed: Breed) {
        runOnBackground {
            breedDataSource.updateFavorite(tBreed.id, tBreed.favorite.not())
        }
    }

    private fun observeBreedsTable() {
        //get Data from db with observe (Flow)
        runOnBackground {
            breedDataSource.getAllBreeds().collect {
                println("data from DB : Size = ${it.size}")
                breedLiveDataObservable.setValue(Either.Success(it))
            }
        }
    }
}

