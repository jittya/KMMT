package com.jittyandiyan.shared.core.network

import com.jittyandiyan.shared.core.functional.Either
import com.jittyandiyan.shared.core.models.NetworkFailure
import io.ktor.client.utils.*
import io.ktor.http.*

abstract class BaseAPI {
    abstract val baseUrl: String
    val httpHelper = HTTPHelper()

    protected fun URLBuilder.apiPath(path: String) {
        takeFrom(baseUrl)
        encodedPath = path
    }

    suspend inline fun <reified T> doGet(
        urlBuilder: URLBuilder.() -> Unit
    ): Either<T, NetworkFailure> {
        return try {
            val result = httpHelper.doGet<T> {
                apply(urlBuilder)
            }
            Either.Success(result)
        } catch (e: Exception) {
            Either.Failure(NetworkFailure(e))
        }
    }

    suspend inline fun <reified T> doPost(
        requestBody: Any = EmptyContent,
        urlBuilder: URLBuilder.() -> Unit
    ): Either<T, NetworkFailure> {
        return try {
            val result = httpHelper.doPost<T>(requestBody) {
                apply(urlBuilder)
            }
            Either.Success(result)
        } catch (e: Exception) {
            Either.Failure(NetworkFailure(e))
        }
    }
}