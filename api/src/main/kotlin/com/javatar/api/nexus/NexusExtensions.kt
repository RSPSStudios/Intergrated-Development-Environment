package com.javatar.api.nexus

import com.javatar.api.nexus.api.NexusComponentAPI
import com.javatar.api.nexus.api.NexusCredentialsAPI

fun NexusClient.components() : NexusComponentAPI = this
fun NexusClient.user(): NexusCredentialsAPI = this