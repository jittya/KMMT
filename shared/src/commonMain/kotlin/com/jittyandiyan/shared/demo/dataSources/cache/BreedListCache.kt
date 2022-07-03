package com.jittyandiyan.shared.demo.dataSources.cache

import com.jittyandiyan.mobile.TBreed
import com.kmmt.core.dataSync.BaseDataCache
import com.kmmt.core.functional.Either
import com.kmmt.core.models.DataBaseFailure
import com.kmmt.core.models.Failure
import com.jittyandiyan.shared.demo.dataSources.apis.BreedServiceAPI
import com.jittyandiyan.shared.demo.dataSources.localDB.BreedTableHelper
import kotlinx.coroutines.CoroutineScope

class BreedListCache(backgroundCoroutineScope: CoroutineScope) :
    BaseDataCache<Unit, List<TBreed>>(backgroundCoroutineScope, "BREED_SYNC_TIME") {

    override suspend fun getData(param: Unit): Either<List<TBreed>, Failure> {
        return BreedServiceAPI().getBreeds()
    }

    override suspend fun saveData(data: List<TBreed>): Either<Boolean, Failure> {
        return try {
            BreedTableHelper().insertBreeds(data)
            Either.Success(true)
        } catch (e: Exception) {
            Either.Failure(DataBaseFailure(e))
        }
    }
}