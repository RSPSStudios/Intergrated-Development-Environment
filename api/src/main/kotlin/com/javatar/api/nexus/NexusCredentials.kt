package com.javatar.api.nexus

class NexusCredentials(val user: String, val pass: String) {

    companion object {
        val ANONYMOUS = NexusCredentials("anonymous", "")
    }
}