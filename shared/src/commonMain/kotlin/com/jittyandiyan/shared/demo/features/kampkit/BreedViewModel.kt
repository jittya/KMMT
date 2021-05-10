package com.jittyandiyan.shared.demo.features.kampkit

import com.jittyandiyan.mobile.TBreed
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import com.jittyandiyan.shared.core.keyValueStore.getStoreValue
import com.jittyandiyan.shared.core.keyValueStore.storeValue
import com.jittyandiyan.shared.core.liveData.LiveDataObservable
import com.jittyandiyan.shared.core.platform.runOnAndroid
import com.jittyandiyan.shared.demo.dataSources.apis.BreedServiceAPI
import com.jittyandiyan.shared.demo.dataSources.localDB.BreedTableHelper
import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.collect

class BreedViewModel(view: BreedView) : BaseViewModel<BreedView>(view) {

    lateinit var breedTableHelper: BreedTableHelper
    lateinit var breedLiveDataObservable: LiveDataObservable<List<TBreed>>
    val BREED_SYNC_TIME_KEY = "BREED_SYNC_TIME"

    override fun onStartViewModel() {

        runOnAndroid {
            getView()?.setPageTitle("Breed List")
        }

        breedTableHelper = BreedTableHelper()

        breedLiveDataObservable = observe { breedList ->
            getView()?.refreshBreedList(breedList)
            getView()?.stopRefreshing()
        }

        getBreedsFromAPIThenCache()

        observeBreedsTable()

        getView()?.setBreedRefreshAction(this::refresh)
        getView()?.setBreedFavouriteClickAction(this::invertBreedFavouriteState)
    }

    private fun refresh(forceRefresh: Boolean) {
        getBreedsFromAPIThenCache(forceRefresh)
    }

    private fun invertBreedFavouriteState(tBreed: TBreed) {
        runOnBackground {
            breedTableHelper.updateFavorite(tBreed.id, tBreed.favorite.not())
        }
    }

    private fun getBreedsFromAPIThenCache(forceRefresh: Boolean = false) {
        if (isSyncExpired() || forceRefresh) {
            //get Data from API and save to DB
            runOnBackground {
                BreedServiceAPI().getBreeds().let { breedResult ->
                    breedResult.eitherAsync({
                        runOnUI {
                            getView()?.showPopUpMessage(it.message)
                            getView()?.stopRefreshing()
                        }
                    }, { breedResult ->
                        storeValue { putLong(BREED_SYNC_TIME_KEY, DateTime.nowLocal().local.unixMillisLong) }
                        breedResult.message.keys
                            .sorted().toList()
                            .map { TBreed(0L, name = it.toWordCaps(), false) }
                            .let {
                                println("New Data from Server : Size = ${it.size}")
                                //This table insert will trigger data change and value will be available in observer
                                breedTableHelper.insertBreeds(it)
                            }
                    })

                }
            }
        } else {
            println("isSyncExpired not expired")
            getView()?.stopRefreshing()
        }
    }

    private fun isSyncExpired(): Boolean {
        val lastSyncTime = getStoreValue<Long>(BREED_SYNC_TIME_KEY)
        if (lastSyncTime != null) {
            val oneHourMS = 30 * 1000
            return ((lastSyncTime + oneHourMS) < DateTime.nowLocal().local.unixMillisLong)
        } else {
            return true
        }
    }

    private fun observeBreedsTable() {
        //get Data from db with observe (Flow)
        runOnBackground {
            breedTableHelper.getAllBreeds().collect {
                println("data from DB : Size = ${it.size}")
                breedLiveDataObservable.setValue(it)
            }
        }
    }

    fun String.toWordCaps(): String {
        val words = this.split(" ")

        var newStr = ""

        words.forEach {
            newStr += it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } + " "
        }
        return newStr.trim()
    }
}

