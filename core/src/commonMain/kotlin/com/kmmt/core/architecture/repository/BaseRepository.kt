package com.kmmt.core.architecture.repository

import com.kmmt.core.functional.Either
import com.kmmt.core.models.Failure

interface BaseRepository<in InputParamsType, ReturnType> {
    suspend fun execute(inputParamsType: InputParamsType): Either<ReturnType, Failure>
}