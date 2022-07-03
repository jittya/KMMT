package com.jittyandiyan.shared.core.architecture.usecase

import com.jittyandiyan.shared.core.architecture.presenter.async.Async
import com.jittyandiyan.shared.core.functional.Either
import com.jittyandiyan.shared.core.models.Failure
import io.ktor.utils.io.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<in InputParamsType, ReturnType>(
    private val taskLimit: TaskLimit = TaskLimit.Single
) where ReturnType : Any, InputParamsType : Any {
    private lateinit var async: Async

    constructor(async: Async, taskLimit: TaskLimit = TaskLimit.Single) : this(taskLimit) {
        this.async = async
    }

    fun setAsync(async: Async)
    {
        this.async = async
    }

    private lateinit var taskDeferred: Deferred<Either<ReturnType, Failure>>

    abstract suspend fun run(params: InputParamsType): Either<ReturnType, Failure>

    operator fun invoke(params: InputParamsType): Flow<Either<ReturnType, Failure>> = flow {
        if (taskLimit == TaskLimit.Single) {
            cancelPendingTasks()
        }
        taskDeferred = async.getBackgroundCoroutineScope().async {
            run(params)
        }
        emit(taskDeferred.await())
    }

    fun cancelPendingTasks() {
        if (this@BaseUseCase::taskDeferred.isInitialized) {
            taskDeferred.cancel(CancellationException("New Request came"))
        }
    }

    enum class TaskLimit {
        Single,
        Multiple
    }
}