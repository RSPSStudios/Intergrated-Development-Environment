package com.javatar.api.nexus

import com.javatar.api.nexus.api.NexusComponentAPI
import com.javatar.api.nexus.api.data.ComponentList
import com.javatar.api.nexus.api.data.NexusComponent
import com.javatar.api.nexus.api.NexusCredentials
import com.javatar.api.nexus.api.NexusURL
import com.javatar.api.nexus.api.ThirdPartyRepositoryAccess
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class NexusClient : NexusComponentAPI, NexusCredentials, NexusURL, ThirdPartyRepositoryAccess {

    private var username: String = ""
    private var password: String = ""
    private var baseURL: String = ""

    private val apiUrl = "$baseURL/service/rest/v1/"
    private val client = HttpClient(Apache)
    private val encoder = Base64.getEncoder()
    private val base64Credentials: String
        get() = encoder.encodeToString("$username:$password".toByteArray())

    override fun getComponentList(repository: String, token: String): Flow<ComponentList> = flow {
        var queriedURL = "${apiUrl}/components?repository=$repository"
        if (token.isNotEmpty()) {
            queriedURL += "?continuationToken=$token"
        }
        emit(client.get(queriedURL) {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                headers["Authentication"] = "Basic $base64Credentials"
            }
        })
    }

    override fun getComponentByID(id: String): Flow<NexusComponent> = flow {
        val queriedURL = "${apiUrl}/components/$id"
        emit(client.get(queriedURL) {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                headers["Authentication"] = "Basic $base64Credentials"
            }
        })
    }

    override fun user(username: String, password: String) {
        this.username = username
        this.password = password
    }

    override fun baseURL(base: String) {
        baseURL = base
    }

    override fun use(baseURL: String, action: NexusClient.() -> Unit) {
        val oldURL = this.baseURL
        baseURL(baseURL)
        action(this)
        baseURL(oldURL)
    }

}