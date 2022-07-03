package com.jittyandiyan.shared.demo.dataSources.localDB

import com.jittyandiyan.mobile.TBreed
import com.kmmt.core.extensions.transactionWithContext
import com.kmmt.core.localDB.DBHelper
import com.kmmt.core.platform.expectations.Dispatchers_Default
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class BreedTableHelper : DBHelper() {

    fun getAllBreeds(): Flow<List<TBreed>> =
        localDB.tBreedQueries
            .selectAll()
            .asFlow()
            .mapToList()
            .flowOn(Dispatchers_Default)


    suspend fun insertBreeds(breeds: List<TBreed>) {
        println("Inserting ${breeds.size} breeds into database")
        localDB.transactionWithContext(Dispatchers_Default) {
            breeds.forEach { breed ->
                localDB.tBreedQueries.insertBreed(null, breed.name)
            }
        }
    }

    fun selectById(id: Long): Flow<List<TBreed>> =
        localDB.tBreedQueries
            .selectById(id)
            .asFlow()
            .mapToList()
            .flowOn(Dispatchers_Default)

    suspend fun deleteAll() {
        println("Database Cleared")
        localDB.transactionWithContext(Dispatchers_Default) {
            localDB.tBreedQueries.deleteAll()
        }
    }

    suspend fun updateFavorite(breedId: Long, favorite: Boolean) {
        println("Breed $breedId: Favorited $favorite")
        localDB.transactionWithContext(Dispatchers_Default) {
            localDB.tBreedQueries.updateFavorite(favorite, breedId)
        }
    }
}