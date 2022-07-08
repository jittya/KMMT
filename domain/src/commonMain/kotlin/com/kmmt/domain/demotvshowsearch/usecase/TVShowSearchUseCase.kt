package com.kmmt.domain.demotvshowsearch.usecase

import com.kmmt.domain.demotvshowsearch.repository.ITVShowSearchRepository
import com.kmmt.common.functional.Either
import com.kmmt.common.models.Failure
import com.kmmt.core.architecture.usecase.BaseRepositoryUseCase
import com.kmmt.core.architecture.usecase.BaseUseCase
import com.kmmt.models.demotvshowsearch.domain.TVShowInfo
import com.kmmt.network.apis.TVMazeAPI

class TVShowSearchUseCase() : BaseUseCase<String, List<TVShowInfo>>() {
    override suspend fun run(params: String): Either<List<TVShowInfo>, Failure> {
        return TVMazeAPI().getTVShows(params)
    }
}

class TVShowSearchRepoUseCase(
    repository: ITVShowSearchRepository
) : BaseRepositoryUseCase<String, List<TVShowInfo>>( repository)