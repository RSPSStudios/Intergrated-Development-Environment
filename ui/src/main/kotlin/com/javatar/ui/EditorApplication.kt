package com.javatar.ui

import com.javatar.api.fs.IFileTypeManager
import com.javatar.api.fs.extensions.ArchiveTypeExtension
import com.javatar.api.fs.extensions.FileTypeExtension
import com.javatar.api.fs.extensions.IndexTypeExtension
import com.javatar.ui.models.PluginRepositoryModel
import com.javatar.ui.views.MainView
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import tornadofx.App

class EditorApplication : App(MainView::class), KoinComponent {

    val pluginRepo: PluginRepositoryModel = get()
    val typeManager: IFileTypeManager = get()

    override fun init() {
        super.init()
        pluginRepo.manager.getExtensions(FileTypeExtension::class.java).forEach {
            typeManager.registerFileType(it.createFileType())
        }
        pluginRepo.manager.getExtensions(ArchiveTypeExtension::class.java).forEach {
            typeManager.registerArchiveType(it.createArchiveType())
        }
        pluginRepo.manager.getExtensions(IndexTypeExtension::class.java).forEach {
            typeManager.registerIndexType(it.createIndexType())
        }
    }
}
