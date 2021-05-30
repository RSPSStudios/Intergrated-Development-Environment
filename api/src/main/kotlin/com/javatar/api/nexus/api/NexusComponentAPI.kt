package com.javatar.api.nexus.api

import com.javatar.api.nexus.api.data.ComponentList
import com.javatar.api.nexus.api.data.NexusComponent
import kotlinx.coroutines.flow.Flow

interface NexusComponentAPI {

    fun getComponentList(repository: String, token: String = ""): Flow<ComponentList>

    fun getComponentByID(id: String) : Flow<NexusComponent>

}