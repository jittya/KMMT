package com.jittyandiyan.shared.demo.features.kampkit

import com.kmmt.core.architecture.presenter.BasePresenter
import com.kmmt.common.functional.Either
import com.kmmt.core.liveData.LiveDataObservable
import com.kmmt.common.models.Failure
import com.kmmt.domain.demo.cache.BreedListCache
import com.kmmt.common.platforms.runOnAndroid
import com.kmmt.models.demo.domain.Breed
import com.kmmt.persistance.dataSources.breed.BreedDataSource
import com.kmmt.resources.Resources
import com.kmmt.resources.expectations.localized
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class BreedPresenter(view: BreedView, ) : BasePresenter<BreedView>(view),KoinComponent {

    private val breedDataSource: BreedDataSource by inject()
    private lateinit var breedLiveDataObservable: LiveDataObservable<Either<List<Breed>, Failure>>
    private val breedListCache: BreedListCache by inject { parametersOf(getBackgroundCoroutineScope()) }

    override fun onStartPresenter() {

        runOnAndroid {
            getView()?.setPageTitle(Resources.strings.breedList.localized())
        }

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

