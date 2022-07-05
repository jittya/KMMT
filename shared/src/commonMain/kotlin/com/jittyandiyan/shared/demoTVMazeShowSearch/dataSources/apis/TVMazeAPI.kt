package com.jittyandiyan.shared.demoTVMazeShowSearch.dataSources.apis

import com.kmmt.common.functional.Either
import com.kmmt.common.functional.map
import com.kmmt.core.models.NetworkFailure
import com.kmmt.core.network.BaseAPI
import com.jittyandiyan.shared.demoTVMazeShowSearch.models.TMDBResult
import com.jittyandiyan.shared.demoTVMazeShowSearch.models.TVShowInfo
import io.ktor.http.*

class TVMazeAPI : BaseAPI() {
    override val baseUrl: String
        get() = "https://api.themoviedb.org/"
 //   https://api.themoviedb.org/3/search/tv?api_key=2b24827469d6d5b0fd06aed8a5e2d358&query=money

    suspend fun getTVShows(query: String): Either<List<TVShowInfo>, NetworkFailure> {
        //Converting DTO to Application Domain Object

        return doGet<TMDBResult>(
            "3/search/tv?query=${query.encodeURLPath()}&api_key=2b24827469d6d5b0fd06aed8a5e2d358"
        ).map { tvShowDTOItem ->
            println("Size : "+tvShowDTOItem.results.size)
            tvShowDTOItem.results.map {
                TVShowInfo(
                    it.id.toLong(),
                    it.name,
                    it.originalLanguage,
                    it.posterPath,
                    it.overview
                )
            }
        }
    }
}