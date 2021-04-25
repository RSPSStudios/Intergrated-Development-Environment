package com.javatar.api.http

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.File
import kotlin.experimental.and
import kotlin.text.toByteArray

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

    fun downloadFile(url: String, outFile: File, credentials: Credentials?) = flow {
        emitAll(client.downloadFile(outFile, url, credentials))
    }


    inline fun <reified T> post(endpoint: String, body: Any, credentials: Credentials? = null) = flow<T> {
        emit(client.post("http://$DOMAIN:8080/$endpoint") {
            contentType(ContentType.Application.Json)
            if (credentials != null) {
                header(HttpHeaders.Authorization, credentials.constructBasicAuthValue())
            }
            this.body = body
        })
    }

    inline fun <reified T> get(endpoint: String, credentials: Credentials? = null) = flow<T> {
        emit(client.get("http://$DOMAIN:8080/$endpoint") {
            contentType(ContentType.Application.Json)
            if (credentials != null) {
                header(HttpHeaders.Authorization, credentials.constructBasicAuthValue())
            }
        })
    }

    fun Credentials.constructBasicAuthValue(): String {
        val authString = "$email:$password"
        val authBuf = authString.toByteArray(defaultCharset).encodeBase64()

        return "Basic $authBuf"
    }

    fun ByteArray.encodeBase64(): String = buildPacket {
        writeFully(this@encodeBase64)
    }.encodeBase64()

    private val BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    private val BASE64_MASK: Byte = 0x3f
    private val BASE64_PAD = '='

    fun ByteReadPacket.encodeBase64(): String = buildString {
        val data = ByteArray(3)
        while (remaining > 0) {
            val read = readAvailable(data)
            data.clearFrom(read)

            val padSize = (data.size - read) * 8 / 6
            val chunk = ((data[0].toInt() and 0xFF) shl 16) or
                    ((data[1].toInt() and 0xFF) shl 8) or
                    (data[2].toInt() and 0xFF)

            for (index in data.size downTo padSize) {
                val char = (chunk shr (6 * index)) and BASE64_MASK.toInt()
                append(char.toBase64())
            }

            repeat(padSize) { append(BASE64_PAD) }
        }
    }

    private val BASE64_INVERSE_ALPHABET = IntArray(256) {
        BASE64_ALPHABET.indexOf(it.toChar())
    }

    private fun Int.toBase64(): Char = BASE64_ALPHABET[this]
    private fun Byte.fromBase64(): Byte =
        BASE64_INVERSE_ALPHABET[toInt() and 0xff].toByte() and BASE64_MASK
    private fun ByteArray.clearFrom(from: Int) {
        (from until size).forEach { this[it] = 0 }
    }

    companion object {
        const val DOMAIN = "legionkt.com"
    }
}
