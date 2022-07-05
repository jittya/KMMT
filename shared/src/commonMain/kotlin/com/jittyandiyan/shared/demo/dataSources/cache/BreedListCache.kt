package com.jittyandiyan.shared.demo.dataSources.cache

import com.kmmt.core.dataSync.BaseDataCache
import com.kmmt.common.functional.Either
import com.kmmt.core.models.DataBaseFailure
import com.kmmt.core.models.Failure
import com.jittyandiyan.shared.demo.dataSources.apis.BreedServiceAPI
import com.kmmt.persistance.dataSources.breed.Breed
import com.kmmt.persistance.dataSources.breed.BreedDataSource
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent

class BreedListCache(backgroundCoroutineScope: CoroutineScope, private val breedDataSource: BreedDataSource) :
    BaseDataCache<Unit, List<Breed>>(backgroundCoroutineScope, "BREED_SYNC_TIME"),KoinComponent {

    override suspend fun getData(param: Unit): Either<List<Breed>, Failure> {
        return BreedServiceAPI().getBreeds()
    }

    override suspend fun saveData(data: List<Breed>): Either<Boolean, Failure> {
        return try {
            breedDataSource.insertBreeds(data)
            Either.Success(true)
        } catch (e: Exception) {
            Either.Failure(DataBaseFailure(e))
        }
    }
}