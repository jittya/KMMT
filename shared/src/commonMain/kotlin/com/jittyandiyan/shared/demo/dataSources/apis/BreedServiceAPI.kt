package com.jittyandiyan.shared.demo.dataSources.apis

import com.jittyandiyan.shared.core.functional.Either
import com.jittyandiyan.shared.core.models.NetworkFailure
import com.jittyandiyan.shared.core.network.BaseAPI
import com.jittyandiyan.shared.demo.models.Breed.BreedResult

class BreedServiceAPI:BaseAPI() {
    override val baseUrl: String
        get() = "https://dog.ceo/"


    suspend fun getBreeds(): Either<BreedResult, NetworkFailure> {
        return doGet {
            apiPath("api/breeds/list/all")
        }
    }
}