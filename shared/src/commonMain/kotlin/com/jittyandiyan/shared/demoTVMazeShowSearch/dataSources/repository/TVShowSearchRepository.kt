package com.jittyandiyan.shared.demoTVMazeShowSearch.dataSources.repository

import com.jittyandiyan.shared.demoTVMazeShowSearch.dataSources.apis.TVMazeAPI
import com.jittyandiyan.shared.demoTVMazeShowSearch.models.TVShowInfo
import com.jittyandiyan.shared.core.architecture.repository.BaseRepository
import com.jittyandiyan.shared.core.functional.Either
import com.jittyandiyan.shared.core.models.Failure


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