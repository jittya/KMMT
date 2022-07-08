package com.kmmt.core.architecture.repository

import com.kmmt.common.functional.Either
import com.kmmt.common.models.Failure

interface BaseRepository<in InputParamsType, ReturnType> {
    suspend fun execute(inputParamsType: InputParamsType): Either<ReturnType, Failure>
}