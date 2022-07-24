package com.kmmt.network.apis

import com.kmmt.common.extensions.toWordCaps
import com.kmmt.common.functional.Either
import com.kmmt.common.functional.flatMap
import com.kmmt.common.models.NetworkFailure
import com.kmmt.models.demo.domain.Breed
import com.kmmt.models.demo.dto.BreedResult
import com.kmmt.network.core.BaseAPI

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


