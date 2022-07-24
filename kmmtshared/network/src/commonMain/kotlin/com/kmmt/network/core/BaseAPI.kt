package com.kmmt.network.core

import com.kmmt.common.functional.Either
import com.kmmt.common.models.NetworkFailure
import io.ktor.client.utils.*
import io.ktor.http.*

abstract class BaseAPI {
    abstract val baseUrl: String
    val httpHelper = HTTPHelper()

    fun URLBuilder.apiPath(endPoint: String, urlBuilder: URLBuilder.() -> Unit) {
        takeFrom(baseUrl)
        encodedPath = endPoint
        apply(urlBuilder)
    }

    suspend inline fun <reified T> doGet(
        endPoint: String,
        noinline urlBuilder: URLBuilder.() -> Unit = {}
    ): Either<T, NetworkFailure> {
        return try {
            val result = httpHelper.doGet<T> {
                apply {
                    apiPath(endPoint,urlBuilder)
                }
            }
            Either.Success(result)
        } catch (e: Exception) {
            Either.Failure(NetworkFailure(e))
        }
    }

    suspend inline fun <reified T> doPost(
        endPoint: String,
        requestBody: Any = EmptyContent,
        noinline urlBuilder: URLBuilder.() -> Unit ={}
    ): Either<T, NetworkFailure> {
        return try {
            val result = httpHelper.doPost<T>(requestBody) {
                apply {
                    apiPath(endPoint,urlBuilder)
                }
            }
            Either.Success(result)
        } catch (e: Exception) {
            Either.Failure(NetworkFailure(e))
        }
    }
}