package com.javatar.application.plugins

import org.pf4j.ManifestPluginDescriptorFinder
import org.pf4j.util.FileUtils
import java.nio.file.Files
import java.nio.file.Path

class LinuxManifestPluginDescriptorFinder : ManifestPluginDescriptorFinder() {
    override fun getManifestPath(pluginPath: Path): Path? {
        return if (Files.isDirectory(pluginPath)) {
            FileUtils.findFile(
                pluginPath.resolve("classes"), "MANIFEST.MF"
            )
        } else null
    }
}