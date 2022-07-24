package com.kmmt.persistance.dataSources.breed

import com.kmmt.common.expectations.DispatcherDefault
import com.kmmt.models.demo.domain.Breed
import com.kmmt.persistance.storage.realm.BreedR
import com.kmmt.persistance.storage.realm.RealmHelper
import com.kmmt.persistance.storage.realm.mapToBreed
import com.kmmt.persistance.storage.realm.nextBreedID
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class BreedRealmDataSource(private val breedClass: BreedR) : BreedDataSource,
    RealmHelper(breedClass) {
    override fun getAllBreeds(): Flow<List<Breed>> {
        return realm.query<BreedR>().asFlow().mapToList().map {
            it.mapToBreed()
        }.flowOn(DispatcherDefault)
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
        }.flowOn(DispatcherDefault)
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