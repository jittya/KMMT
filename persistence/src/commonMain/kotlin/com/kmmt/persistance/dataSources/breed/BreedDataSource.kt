package com.kmmt.persistance.dataSources.breed

import kotlinx.coroutines.flow.Flow

interface BreedDataSource {
    fun getAllBreeds(): Flow<List<Breed>>
    suspend fun insertBreeds(breeds: List<Breed>)
    fun selectById(id: Long): Flow<List<Breed>>
    suspend fun deleteAll()
    suspend fun updateFavorite(breedId: Long, favorite: Boolean)
}