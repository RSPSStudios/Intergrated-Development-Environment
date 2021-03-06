package com.javatar.ui.data

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

data class PluginInformation(
    val pluginName: String,
    val pluginAuthor: String,
    val pluginDescriptor: String,
    val pluginVersion: String,
    val pluginEnabled: Boolean,
    val updateAvailable: Boolean,
    val isInstalled: Boolean,
    var sha1: String = "Deprecated",
    var sha256: String = "Not Used",
    var sha512: String = "Unknown",
    var md5: String = "Deprecated"
)
