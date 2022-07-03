package com.jittyandiyan.shared.core.architecture.usecase

import com.jittyandiyan.shared.core.architecture.repository.BaseRepository
import com.jittyandiyan.shared.core.architecture.presenter.async.Async
import com.jittyandiyan.shared.core.functional.Either
import com.jittyandiyan.shared.core.models.Failure

open class BaseRepositoryUseCase<in InputParamsType, ReturnType>(
    private val repository: BaseRepository<InputParamsType, ReturnType>,
    taskLimit: TaskLimit = TaskLimit.Single
) : BaseUseCase<InputParamsType, ReturnType>(taskLimit) where ReturnType : Any, InputParamsType : Any {
    private lateinit var async: Async

    constructor(
        async: Async,
        repository: BaseRepository<InputParamsType, ReturnType>,
        taskLimit: TaskLimit = TaskLimit.Single
    ) : this(repository, taskLimit) {
        this.async = async
    }


    override suspend fun run(params: InputParamsType): Either<ReturnType, Failure> {
        return repository.execute(params)
    }
}