package com.javatar.api.http

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.flow.flow

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 22 2021
 */
class Client {

    private val defaultCharset = Charsets.ISO_8859_1

    val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Auth)
    }


    @InternalAPI
    inline fun <reified T> post(endpoint: String, body: Any, credentials: Credentials? = null) = flow<T> {
        emit(client.post("http://127.0.0.1:8080/$endpoint") {
            contentType(ContentType.Application.Json)
            if (credentials != null) {
                header(HttpHeaders.Authorization, credentials.constructBasicAuthValue())
            }
            this.body = body
        })
    }

    @InternalAPI
    inline fun <reified T> get(endpoint: String, credentials: Credentials? = null) = flow<T> {
        emit(client.get("http://127.0.0.1:8080/$endpoint") {
            contentType(ContentType.Application.Json)
            if (credentials != null) {
                header(HttpHeaders.Authorization, credentials.constructBasicAuthValue())
            }
        })
    }

    @InternalAPI
    fun Credentials.constructBasicAuthValue(): String {
        val authString = "$email:$password"
        val authBuf = authString.toByteArray(defaultCharset).encodeBase64()

        return "Basic $authBuf"
    }

}
