package com.jittyandiyan.shared.demoTVMazeShowSearch.dataSources.repository

import com.jittyandiyan.shared.demoTVMazeShowSearch.dataSources.apis.TVMazeAPI
import com.jittyandiyan.shared.demoTVMazeShowSearch.models.TVShowInfo
import com.kmmt.core.architecture.repository.BaseRepository
import com.kmmt.common.functional.Either
import com.kmmt.core.models.Failure


interface ITVShowSearchRepository : BaseRepository<String, List<TVShowInfo>>

class TVShowSearchRepositoryTVMazeAPI : ITVShowSearchRepository {
    override suspend fun execute(inputParamsType: String): Either<List<TVShowInfo>, Failure> {
        return TVMazeAPI().getTVShows(inputParamsType)
    }
}

class TVShowSearchRepositoryMock : ITVShowSearchRepository {
    override suspend fun execute(inputParamsType: String): Either<List<TVShowInfo>, Failure> {
        return Either.Success(listOf(TVShowInfo(11,"Test","Test","Test","Test")))
    }
}