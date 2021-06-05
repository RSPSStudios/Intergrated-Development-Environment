package com.javatar.api.nexus

import com.google.gson.Gson
import com.javatar.api.nexus.api.*
import com.javatar.api.nexus.api.data.AssetList
import com.javatar.api.nexus.api.data.ComponentList
import com.javatar.api.nexus.api.data.NexusAsset
import com.javatar.api.nexus.api.data.NexusComponent
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class NexusClient : NexusComponentAPI, NexusCredentialsAPI, NexusURL, ThirdPartyRepositoryAccess, NexusAssetAPI {

    private var username: String = ""
    private var password: String = ""
    private var baseURL: String = ""

    private val apiUrl: String
        get() = "$baseURL/service/rest/v1"
    private val client = HttpClient(Apache)
    private val encoder = Base64.getEncoder()
    private val base64Credentials: String
        get() = encoder.encodeToString("$username:$password".toByteArray())
    private val gson = Gson()

    val baseDownloadURL: String
        get() = "${baseURL}/repository"

    override fun getComponentList(repository: String, token: String): Flow<ComponentList> = flow {
        var queriedURL = "${apiUrl}/components?repository=$repository"
        if (token.isNotEmpty()) {
            queriedURL += "?continuationToken=$token"
        }
        val json = client.get<String>(queriedURL) {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                headers["Authentication"] = "Basic $base64Credentials"
            }
        }
        emit(
            gson.fromJson(json, ComponentList::class.java)
        )
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

    override fun assets(repository: String, token: String): Flow<AssetList> = flow {
        var queriedURL = "${apiUrl}/assets?repository=$repository"
        if (token.isNotEmpty()) {
            queriedURL += "?continuationToken=$token"
        }
        val json = client.get<String>(queriedURL) {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                headers["Authentication"] = "Basic $base64Credentials"
            }
        }
        emit(
            gson.fromJson(json, AssetList::class.java)
        )
    }

    override fun getAsset(assetId: String): Flow<NexusAsset> = flow {
        val queriedURL = "${apiUrl}/assets/$assetId"
        emit(client.get(queriedURL) {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                headers["Authentication"] = "Basic $base64Credentials"
            }
        })
    }


}