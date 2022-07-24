package com.kmmt.domain.demotvshowsearch.repository

import com.kmmt.models.demotvshowsearch.domain.TVShowInfo
import com.kmmt.core.architecture.repository.BaseRepository
import com.kmmt.common.functional.Either
import com.kmmt.common.models.Failure
import com.kmmt.network.apis.TVMazeAPI


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