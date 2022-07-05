package com.jittyandiyan.shared.demo.dataSources.apis

import com.jittyandiyan.shared.demo.models.Breed.BreedResult
import com.kmmt.core.extensions.toWordCaps
import com.kmmt.common.functional.Either
import com.kmmt.common.functional.flatMap
import com.kmmt.core.models.NetworkFailure
import com.kmmt.core.network.BaseAPI
import com.kmmt.persistance.dataSources.breed.Breed

class BreedServiceAPI : BaseAPI() {
    override val baseUrl: String
        get() = "https://dog.ceo/"

    suspend fun getBreeds(): Either<List<Breed>, NetworkFailure> {
        return doGet<BreedResult>("api/breeds/list/all").flatMap { breedResult ->
            //Converting BreedResult to List<TBreed>
            Either.Success(
                breedResult.message.keys
                    .sorted().toList()
                    .map { Breed(0L, name = it.toWordCaps(), false) }
            )
        }
    }
}


