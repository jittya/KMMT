package com.jittyandiyan.shared.core.network

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
    ): T {
        return httpHelper.doGet {
            apply(urlBuilder)
        }
    }

    suspend inline fun <reified T> doPost(
        requestBody: Any = EmptyContent,
        urlBuilder: URLBuilder.() -> Unit
    ): T {
        return httpHelper.doPost(requestBody) {
            apply(urlBuilder)
        }
    }
}