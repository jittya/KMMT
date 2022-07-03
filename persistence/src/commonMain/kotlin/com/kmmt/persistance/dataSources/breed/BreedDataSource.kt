package com.kmmt.persistance.dataSources.breed

import com.jittyandiyan.mobile.KMMTDB
import com.kmmt.persistance.database.realm.*
import com.kmmt.persistance.database.sqlite.SQLDelightHelper
import com.kmmt.persistance.extensions.transactionWithContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import com.kmmt.persistance.database.sqlite.mapToBreed
import com.kmmt.persistance.expectations.Dispatchers_Default
import io.realm.kotlin.ext.query

interface BreedDataSource {
    fun getAllBreeds(): Flow<List<Breed>>
    suspend fun insertBreeds(breeds: List<Breed>)
    fun selectById(id: Long): Flow<List<Breed>>
    suspend fun deleteAll()
    suspend fun updateFavorite(breedId: Long, favorite: Boolean)
}

class BreedSQLiteDataSource(private val kmmtSQLDelight: KMMTDB) : BreedDataSource,
    SQLDelightHelper() {

    override fun getAllBreeds(): Flow<List<Breed>> =
        kmmtSQLDelight.tBreedQueries
            .selectAll()
            .asFlow()
            .mapToList().map {
                it.mapToBreed()
            }
            .flowOn(Dispatchers_Default)


    override suspend fun insertBreeds(breeds: List<Breed>) {
        kmmtSQLDelight.transactionWithContext(Dispatchers_Default) {
            breeds.forEach { breed ->
                kmmtSQLDelight.tBreedQueries.insertBreed(null, breed.name)
            }
        }
    }

    override fun selectById(id: Long): Flow<List<Breed>> =
        kmmtSQLDelight.tBreedQueries
            .selectById(id)
            .asFlow()
            .mapToList().map {
                it.mapToBreed()
            }
            .flowOn(Dispatchers_Default)

    override suspend fun deleteAll() {
        kmmtSQLDelight.transactionWithContext(Dispatchers_Default) {
            kmmtSQLDelight.tBreedQueries.deleteAll()
        }
    }

    override suspend fun updateFavorite(breedId: Long, favorite: Boolean) {
        kmmtSQLDelight.transactionWithContext(Dispatchers_Default) {
            kmmtSQLDelight.tBreedQueries.updateFavorite(favorite, breedId)
        }
    }
}

class BreedRealmDataSource(private val breedClass: BreedR) : BreedDataSource,
    RealmHelper(breedClass) {
    override fun getAllBreeds(): Flow<List<Breed>> {
        return realm.query<BreedR>().asFlow().mapToList().map {
            it.mapToBreed()
        }.flowOn(Dispatchers_Default)
    }

    override suspend fun insertBreeds(breeds: List<Breed>) {
        realm.write {
            val nextID=realm.nextBreedID().toLong()
            breeds.forEachIndexed { index, breed ->
                this.copyToRealm(BreedR().apply {
                    id = nextID+index
                    name = breed.name
                })
            }
        }
    }

    override fun selectById(id: Long): Flow<List<Breed>> {
        return realm.query<BreedR>("id == $0", id).asFlow().mapToList().map {
            it.mapToBreed()
        }.flowOn(Dispatchers_Default)
    }

    override suspend fun deleteAll() {
        realm.write {
            delete(this.query<BreedR>().find())
        }

    }

    override suspend fun updateFavorite(breedId: Long, favorite: Boolean) {
        realm.write {
            val breed: BreedR? =
                this.query<BreedR>("id == $0", breedId).first().find()
            breed?.favorite = favorite
        }
    }

}