package com.jittyandiyan.shared.demoTVMazeShowSearch.features.tvshows

import com.jittyandiyan.shared.demoTVMazeShowSearch.dataSources.apis.TVMazeAPI
import com.jittyandiyan.shared.demoTVMazeShowSearch.dataSources.repository.ITVShowSearchRepository
import com.jittyandiyan.shared.demoTVMazeShowSearch.models.TVShowInfo
import com.jittyandiyan.shared.core.architecture.usecase.BaseUseCase
import com.jittyandiyan.shared.core.architecture.usecase.BaseRepositoryUseCase
import com.jittyandiyan.shared.core.functional.Either
import com.jittyandiyan.shared.core.models.Failure

class TVShowSearchUseCase() : BaseUseCase<String, List<TVShowInfo>>() {
    override suspend fun run(params: String): Either<List<TVShowInfo>, Failure> {
        return TVMazeAPI().getTVShows(params)
    }
}

class TVShowSearchRepoUseCase(
    repository: ITVShowSearchRepository
) : BaseRepositoryUseCase<String, List<TVShowInfo>>( repository)