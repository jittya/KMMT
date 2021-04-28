package com.jittyandiyan.shared.core.network

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.utils.*
import io.ktor.http.*

class HTTPHelper {

    val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
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
        }
    }

    suspend inline fun <reified T> doPost(
        requestBody: Any = EmptyContent,
        urlBuilder: URLBuilder.()-> Unit
    ): T {
        return client.post {
            this.body=requestBody
            url.apply(urlBuilder)
            contentType(ContentType.Application.Json)
        }
    }
}