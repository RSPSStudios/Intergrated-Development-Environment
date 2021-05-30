package com.javatar.api.nexus

import com.javatar.api.nexus.api.NexusComponentAPI
import com.javatar.api.nexus.api.NexusCredentials

fun NexusClient.components() : NexusComponentAPI = this
fun NexusClient.user(): NexusCredentials = this