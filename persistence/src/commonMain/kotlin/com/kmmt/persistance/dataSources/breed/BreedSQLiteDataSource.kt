package com.kmmt.persistance.dataSources.breed

import com.jittyandiyan.mobile.KMMTDB
import com.kmmt.common.expectations.DispatcherDefault
import com.kmmt.models.demo.domain.Breed
import com.kmmt.persistance.storage.sqlite.SQLDelightHelper
import com.kmmt.persistance.storage.sqlite.extensions.transactionWithContext
import com.kmmt.persistance.storage.sqlite.mapToBreed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class BreedSQLiteDataSource(private val sqlDelight: KMMTDB) : BreedDataSource,
    SQLDelightHelper() {

    override fun getAllBreeds(): Flow<List<Breed>> =
        sqlDelight.tBreedQueries
            .selectAll()
            .asFlow()
            .mapToList().map {
                it.mapToBreed()
            }
            .flowOn(DispatcherDefault)


    override suspend fun insertBreeds(breeds: List<Breed>) {
        sqlDelight.transactionWithContext(DispatcherDefault) {
            breeds.forEach { breed ->
                sqlDelight.tBreedQueries.insertBreed(null, breed.name)
            }
        }
    }

    override fun selectById(id: Long): Flow<List<Breed>> =
        sqlDelight.tBreedQueries
            .selectById(id)
            .asFlow()
            .mapToList().map {
                it.mapToBreed()
            }
            .flowOn(DispatcherDefault)

    override suspend fun deleteAll() {
        sqlDelight.transactionWithContext(DispatcherDefault) {
            sqlDelight.tBreedQueries.deleteAll()
        }
    }

    override suspend fun updateFavorite(breedId: Long, favorite: Boolean) {
        sqlDelight.transactionWithContext(DispatcherDefault) {
            sqlDelight.tBreedQueries.updateFavorite(favorite, breedId)
        }
    }
}