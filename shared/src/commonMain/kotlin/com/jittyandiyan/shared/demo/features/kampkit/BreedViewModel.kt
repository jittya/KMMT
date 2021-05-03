package com.jittyandiyan.shared.demo.features.kampkit

import com.jittyandiyan.mobile.TBreed
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import com.jittyandiyan.shared.core.keyValueStore.getStoreValue
import com.jittyandiyan.shared.core.keyValueStore.storeValue
import com.jittyandiyan.shared.core.liveData.LiveDataObservable
import com.jittyandiyan.shared.demo.dataSources.apis.BreedServiceAPI
import com.jittyandiyan.shared.demo.models.Breed.BreedTableHelper
import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.collect

class BreedViewModel(view: BreedView) : BaseViewModel<BreedView>(view) {

    lateinit var breedTableHelper: BreedTableHelper
    lateinit var breedLiveDataObservable: LiveDataObservable<List<TBreed>>
    val BREED_SYNC_TIME_KEY = "BREED_SYNC_TIME"

    override fun onStartViewModel() {
        breedTableHelper = BreedTableHelper()

        breedLiveDataObservable = LiveDataObservable(getLifeCycle())
        breedLiveDataObservable.observe { breedList ->
            //update UI on each value update from table
            getView()?.refreshBreedList(breedList)
        }

        getBreedsFromAPI()

        observeBreedsTable()

    }

    private fun getBreedsFromAPI() {
        if (isSyncExpired()) {
            //get Data from API and save to DB
            runOnBackgroundBlock {
                BreedServiceAPI().getBreeds().let{ breedResult ->
                    storeValue { putLong(BREED_SYNC_TIME_KEY, DateTime.nowLocal().local.unixMillisLong) }
                    breedResult.message.keys
                        .sorted().toList()
                        .map { TBreed(0L, name = it, false) }
                        .let {
                            println("New Data from Server : Size = ${it.size}")
                            //This table insert will trigger data change and value will be available in observer
                            breedTableHelper.insertBreeds(it)
                        }
                }
            }
        }else{
            println("isSyncExpired not expired")
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
        runOnBackgroundBlock {
            breedTableHelper.getAllBreeds().collect {
                println("data from DB : Size = ${it.size}")
                breedLiveDataObservable.setValue(it)
            }
        }
    }
}

