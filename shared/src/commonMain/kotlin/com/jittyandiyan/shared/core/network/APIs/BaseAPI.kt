package com.jittyandiyan.shared.core.network.APIs

import io.ktor.http.*

abstract class BaseAPI {
    abstract val baseUrl: String

    protected fun URLBuilder.apiPath(path: String) {
        takeFrom(baseUrl)
        encodedPath = path
    }
}