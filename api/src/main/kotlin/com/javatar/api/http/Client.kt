package com.javatar.api.http

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import kotlinx.coroutines.flow.flow

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 22 2021
 */

class Client {

    val client = HttpClient(Apache)

    fun use(block: suspend HttpClient.() -> String) = flow {
        emit(block(client))
    }

}
