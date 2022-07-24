package com.kmmt.domain.demo.cache

import com.kmmt.core.dataSync.BaseDataCache
import com.kmmt.common.functional.Either
import com.kmmt.common.models.DataBaseFailure
import com.kmmt.common.models.Failure
import com.kmmt.models.demo.domain.Breed
import com.kmmt.network.apis.BreedServiceAPI
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