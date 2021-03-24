package com.javatar.api.http

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 23 2021
 */

data class ListedPlugin(
    val name: String,
    val description: String,
    val version: String,
    val provider: String
)
