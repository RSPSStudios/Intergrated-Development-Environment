package com.javatar.api.http

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.File

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
    }

    /**
     * Streams file bytes and writes them to provided file, prevents loading the entire response into memory.
     * stream.readChannel and stream.writeChannel are used with coroutines and kotlin to prevent blocking
     */

    @InternalAPI
    suspend fun HttpClient.downloadFile(file: File, url: String, credentials: Credentials?): Flow<DownloadStatus> {
        return flow {
            request<HttpStatement> {
                url(url)
                method = HttpMethod.Get
                if (credentials != null) {
                    header(HttpHeaders.Authorization, credentials.constructBasicAuthValue())
                }
            }.execute {
                val length = it.contentLength()
                if (length != null) {
                    val channel = it.receive<ByteReadChannel>()
                    val outChannel = file.writeChannel()
                    var totalRead = 0L
                    do {
                        val currentRead = channel.copyTo(outChannel, 1024)
                        totalRead += currentRead
                        val progress = (totalRead.toDouble() / length)
                        emit(DownloadStatus.Progress(progress))
                    } while (currentRead > 0)
                    emit(DownloadStatus.Success)
                    outChannel.close()
                }
            }
        }
    }

    @InternalAPI
    fun downloadFile(url: String, outFile: File, credentials: Credentials?) = flow {
        emitAll(client.downloadFile(outFile, url, credentials))
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
