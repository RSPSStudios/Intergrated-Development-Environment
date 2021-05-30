package com.javatar.api.nexus.api.data

class NexusAsset(
    val downloadUrl: String,
    val path: String,
    val id: String,
    val repository: String,
    val format: String,
    val contentType: String,
    val lastModified: String
)