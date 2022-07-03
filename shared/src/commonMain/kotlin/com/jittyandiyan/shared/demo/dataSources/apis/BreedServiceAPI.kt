package com.jittyandiyan.shared.demo.dataSources.apis

import com.jittyandiyan.mobile.TBreed
import com.kmmt.core.extensions.toWordCaps
import com.kmmt.core.functional.Either
import com.kmmt.core.functional.flatMap
import com.kmmt.core.models.NetworkFailure
import com.kmmt.core.network.BaseAPI
import com.jittyandiyan.shared.demo.models.Breed.BreedResult

class BreedServiceAPI : BaseAPI() {
    override val baseUrl: String
        get() = "https://dog.ceo/"

    suspend fun getBreeds(): Either<List<TBreed>, NetworkFailure> {
        return doGet<BreedResult>("api/breeds/list/all") {
        }.flatMap { breedResult ->
            //Converting BreedResult to List<TBreed>
            Either.Success(
                breedResult.message.keys
                    .sorted().toList()
                    .map { TBreed(0L, name = it.toWordCaps(), false) }
            )
        }
    }
}


