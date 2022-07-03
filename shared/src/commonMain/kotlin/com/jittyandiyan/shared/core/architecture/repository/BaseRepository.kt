package com.jittyandiyan.shared.core.architecture.repository

import com.jittyandiyan.shared.core.functional.Either
import com.jittyandiyan.shared.core.models.Failure

interface BaseRepository<in InputParamsType, ReturnType> {
    suspend fun execute(inputParamsType: InputParamsType): Either<ReturnType, Failure>
}