package com.javatar.api.nexus.api.data

class NexusComponent(
    val id: String,
    val repository: String,
    val format: String,
    val group: String,
    val name: String,
    val version: String,
    val assets: Array<NexusAsset>
)