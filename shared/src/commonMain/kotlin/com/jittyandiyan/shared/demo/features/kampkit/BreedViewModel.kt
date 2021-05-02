package com.jittyandiyan.shared.demo.features.kampkit

import com.jittyandiyan.mobile.TBreed
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import com.jittyandiyan.shared.core.liveData.LiveDataObservable
import com.jittyandiyan.shared.demo.dataSources.apis.BreedServiceAPI
import com.jittyandiyan.shared.demo.models.Breed.BreedDBFlowHelper
import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.collect

class BreedViewModel(view: BreedView) : BaseViewModel<BreedView>(view) {

    lateinit var breedDBFlowHelper: BreedDBFlowHelper
    lateinit var breedLiveDataObservable: LiveDataObservable<List<TBreed>>
    val BREED_SYNC_TIME_KEY = "BREED_SYNC_TIME"

    override fun onStartViewModel() {

        breedDBFlowHelper = BreedDBFlowHelper()
        breedLiveDataObservable = LiveDataObservable(getLifeCycle())

        breedLiveDataObservable.observe { breedList ->
            //update UI
            getView()?.refreshBreedList(breedList)
        }

        getBreedsFromAPI()

        observeBreedsTable()

    }

    private fun getBreedsFromAPI() {
        if (isSyncExpired()) {
            //get Data from API and save to DB
            runOnBackground {
                BreedServiceAPI().getBreeds()
            }.resultAsync { breedResult ->
                storeValue { putLong(BREED_SYNC_TIME_KEY, DateTime.nowLocal().local.unixMillisLong) }
                breedResult.message.keys
                    .sorted().toList()
                    .map { TBreed(0L, name = it, false) }
                    .let {
                        println("New Data from Server : Size = ${it.size}")
                        breedDBFlowHelper.insertBreeds(it)
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
        runOnBackground {
            breedDBFlowHelper.getAllBreeds()
        }.resultAsync { flow ->
            flow.collect {
                println("data from DB : Size = ${it.size}")
                breedLiveDataObservable.setValue(it)
            }
        }
    }
}

