package com.jittyandiyan.shared.demoTVMazeShowSearch.features.tvshows

import com.jittyandiyan.shared.demoTVMazeShowSearch.dataSources.apis.TVMazeAPI
import com.jittyandiyan.shared.demoTVMazeShowSearch.dataSources.repository.ITVShowSearchRepository
import com.jittyandiyan.shared.demoTVMazeShowSearch.models.TVShowInfo
import com.kmmt.core.architecture.usecase.BaseUseCase
import com.kmmt.core.architecture.usecase.BaseRepositoryUseCase
import com.kmmt.common.functional.Either
import com.kmmt.core.models.Failure

class TVShowSearchUseCase() : BaseUseCase<String, List<TVShowInfo>>() {
    override suspend fun run(params: String): Either<List<TVShowInfo>, Failure> {
        return TVMazeAPI().getTVShows(params)
    }
}

class TVShowSearchRepoUseCase(
    repository: ITVShowSearchRepository
) : BaseRepositoryUseCase<String, List<TVShowInfo>>( repository)