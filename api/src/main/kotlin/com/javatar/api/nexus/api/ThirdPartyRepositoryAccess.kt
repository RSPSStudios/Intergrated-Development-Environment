package com.javatar.api.nexus.api

import com.javatar.api.nexus.NexusClient

interface ThirdPartyRepositoryAccess {

    fun use(baseURL: String, action: NexusClient.() -> Unit)

}