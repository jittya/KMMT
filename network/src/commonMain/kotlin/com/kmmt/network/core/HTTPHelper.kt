package com.kmmt.network.core

import com.kmmt.common.serialization.JsonSerializationHelper
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class HTTPHelper {

    val client = HttpClient {
        install(ContentNegotiation) {
            json(JsonSerializationHelper.JsonX())
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("HTTPHelper Network > $message")
                }
            }
            level = LogLevel.INFO
        }
    }

    suspend inline fun <reified T> doGet(
         urlBuilder: URLBuilder.()-> Unit
    ): T {
        return client.get {
            url.apply(urlBuilder)
        }.body()
    }

    suspend inline fun <reified T> doPost(
        requestBody: Any = EmptyContent,
        urlBuilder: URLBuilder.()-> Unit
    ): T {
        return client.post {
            setBody(requestBody)
            url.apply(urlBuilder)
            contentType(ContentType.Application.Json)
        }.body()
    }
}