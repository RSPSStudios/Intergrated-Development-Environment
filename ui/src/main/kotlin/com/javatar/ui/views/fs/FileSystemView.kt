package com.javatar.ui.views.fs

import com.javatar.api.fs.directories.ArchiveDirectory
import com.javatar.api.fs.directories.IndexDirectory
import com.javatar.api.ui.fs.ArchiveContextMenuExtension
import com.javatar.api.ui.fs.FileContextMenuExtension
import com.javatar.api.ui.fs.IndexContextMenuExtension
import com.javatar.api.ui.models.EventLogModel
import com.javatar.api.ui.utilities.DataGrid
import com.javatar.api.ui.utilities.contextmenu
import com.javatar.api.ui.utilities.datagrid
import com.javatar.ui.models.ActiveDirectoryModel
import com.javatar.ui.models.ClipboardModel
import com.javatar.ui.models.PluginRepositoryModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.ButtonType
import javafx.scene.control.ContextMenu
import javafx.scene.control.Menu
import javafx.scene.control.TextInputDialog
import javafx.stage.FileChooser
import org.koin.core.component.inject
import tornadofx.*
import java.io.File

class FileSystemView : Fragment() {

    override val scope: FileExplorerScope = super.scope as FileExplorerScope

    val activeDir: ActiveDirectoryModel = scope.activeDirectoryModel

    val clipboardModel: ClipboardModel by di()

    val pluginRepo: PluginRepositoryModel by di()

    val events: EventLogModel by inject()

    val fileTypeManager = scope.typeManager

    override val root = vbox {

        datagrid<FileSystemViewMeta> {
            itemsProperty.bind(activeDir.activeNodes)
            prefWidth = 1032.0
            prefHeight = 705.0

            singleSelect = true

            alignment = Pos.CENTER

            cellWidth = 237.0
            cellHeight = 225.0

            cellCache {
                vbox {
                    spacing = 10.0
                    alignment = Pos.CENTER
                    when (it.meta) {
                        FileSystemViewMeta.MetaType.INDEX -> {
                            val indexId = it.id
                            val type = fileTypeManager.getIndexType(it.id)
                            val root = activeDir.root.get()
                            if (type != null) {
                                val icon = type.icon(root)
                                if (icon != null) {
                                    add(icon)
                                } else {
                                    add(FontAwesomeIconView(FontAwesomeIcon.FOLDER).also { it.glyphSize = 64 })
                                }
                                label(type.identifier(root))
                            } else {
                                add(FontAwesomeIconView(FontAwesomeIcon.FOLDER).also { it.glyphSize = 64 })
                                label("Index ${it.id}")
                            }
                            val exts = pluginRepo.manager.getExtensions(IndexContextMenuExtension::class.java, this@FileSystemView.scope.pluginId)
                            if(exts.isNotEmpty()) {
                                contextmenu {
                                    exts.forEach {  indexExt ->
                                        if(indexId == indexExt.indexId) {
                                            indexExt.configureContextMenu(this, activeDir.root.get())
                                        }
                                    }
                                }
                            }
                            this@datagrid.contextMenu = null
                        }
                        FileSystemViewMeta.MetaType.ARCHIVE -> {
                            val indexId = activeDir.indexDir.get().id
                            val archiveId = it.id
                            val type = fileTypeManager.getArchiveType(indexId, it.id)

                            if (type != null) {
                                val archiveDir = activeDir.indexDir.get().nodes().find { f -> f.id == it.id }
                                if (archiveDir != null && archiveDir.nodes().isNotEmpty()) {
                                    val jfile = archiveDir.nodes()[0]
                                    val archiveIcon = type.icon(archiveDir, activeDir.root.get())
                                    val fileIcon = type.icon(jfile, activeDir.root.get())
                                    when {
                                        archiveIcon != null -> {
                                            add(archiveIcon)
                                        }
                                        fileIcon != null -> {
                                            add(fileIcon)
                                        }
                                        else -> {
                                            add(FontAwesomeIconView(FontAwesomeIcon.FOLDER).also { it.glyphSize = 64 })
                                        }
                                    }
                                    label("${type.identifier(jfile, activeDir.root.get())} ${it.id}")
                                } else {
                                    label("Archive ${it.id}")
                                }
                            } else {
                                add(FontAwesomeIconView(FontAwesomeIcon.FOLDER).also { it.glyphSize = 64 })
                                label("Archive ${it.id}")
                            }
                            contextmenu {
                                addArchiveMenuItems(it, this@datagrid)
                                val exts = pluginRepo.manager.getExtensions(ArchiveContextMenuExtension::class.java, this@FileSystemView.scope.pluginId)
                                if(exts.isNotEmpty()) {
                                    exts.forEach {  archiveExt ->
                                        if(archiveId == archiveExt.archiveId && indexId == archiveExt.indexId) {
                                            archiveExt.configureContextMenu(this, activeDir.root.get())
                                        }
                                    }
                                }
                            }
                        }
                        FileSystemViewMeta.MetaType.FILE -> {
                            val indexId = activeDir.indexDir.get().id
                            val archiveId = activeDir.archiveDir.get().id
                            val type = fileTypeManager.getFileType(indexId, archiveId)
                            val jfile = activeDir.archiveDir.get().nodes().find { f -> f.id == it.id }

                            if (type != null && jfile != null) {
                                val icon = type.icon(jfile, activeDir.root.get())
                                if (icon != null) {
                                    add(icon)
                                } else {
                                    add(FontAwesomeIconView(FontAwesomeIcon.FILE).also { it.glyphSize = 64 })
                                }
                                label(type.identifier(jfile, activeDir.root.get()))
                            } else {
                                add(FontAwesomeIconView(FontAwesomeIcon.FILE).also { it.glyphSize = 64 })
                                label("File ${it.id}")
                            }

                            contextmenu {
                                addFileContextMenuItems(it, this@datagrid)
                                val exts = pluginRepo.manager.getExtensions(FileContextMenuExtension::class.java, this@FileSystemView.scope.pluginId)
                                if(exts.isNotEmpty()) {
                                    exts.forEach {
                                        it.configureContextMenu(this, activeDir.root.get())
                                    }
                                }
                            }
                        }
                    }
                }
            }

            selectionTrigger("action") { it.clickCount == 2 }
            selectionTrigger { it.clickCount == 1 }
            onSelectionChange("action") { meta ->
                when (meta.meta) {
                    FileSystemViewMeta.MetaType.INDEX -> {
                        val indexNode = activeDir.root.get().nodes().find { it.id == meta.id }
                        if (indexNode != null) {
                            val metaNodes =
                                indexNode.nodes().map { FileSystemViewMeta(it.id, FileSystemViewMeta.MetaType.ARCHIVE) }
                            activeDir.indexDir.set(IndexDirectory(indexNode.id, indexNode.parent))
                            activeDir.activeNodes.setAll(FXCollections.observableList(metaNodes))
                            selectionModel.clearSelection()
                        }
                    }
                    FileSystemViewMeta.MetaType.ARCHIVE -> {
                        val indexNode = activeDir.indexDir.get()
                        if (indexNode != null) {
                            val archiveNode = indexNode.nodes().find { it.id == meta.id }
                            if (archiveNode != null) {
                                val metaNodes = archiveNode.nodes()
                                    .map { FileSystemViewMeta(it.id, FileSystemViewMeta.MetaType.FILE) }

                                fileTypeManager.getFileType(indexNode.id, archiveNode.id)
                                    ?.cache(archiveNode.nodes(), activeDir.root.get())

                                activeDir.archiveDir.set(ArchiveDirectory(archiveNode.id, archiveNode.parent))
                                activeDir.activeNodes.set(FXCollections.observableList(metaNodes))
                                selectionModel.clearSelection()
                            }
                        }
                    }
                    FileSystemViewMeta.MetaType.FILE -> {
                        val indexId = activeDir.indexDir.get().id
                        val archiveNode = activeDir.archiveDir.get()
                        val archiveId = archiveNode.id
                        val type = fileTypeManager.getFileType(indexId, archiveId)
                        val jfile = archiveNode.nodes().find { it.id == meta.id }
                        if(jfile != null && type != null) {
                            type.open(jfile, activeDir.root.get(), this@FileSystemView.scope.editorTab)
                        }
                    }
                }
            }

        }
    }

    private fun ContextMenu.addFileContextMenuItems(
        it: FileSystemViewMeta,
        dataGrid: DataGrid<FileSystemViewMeta>
    ) {
        dataGrid.contextmenu { menu("New") { addNewFileItem() } }
        menu("New") { addNewFileItem() }
        separator()
        item("Copy").action {
            val indexId = activeDir.indexDir.get().id
            val archiveId = activeDir.archiveDir.get().id
            val cache = activeDir.root.get().cache
            val data = cache.data(indexId, archiveId, it.id)
            if (data != null) {
                clipboardModel.data.set(data)
                clipboardModel.commit(clipboardModel.data)
            }
        }
        item("Paste") {
            disableWhen(clipboardModel.data.isNull)
            action {
                val indexId = activeDir.indexDir.get().id
                val archiveId = activeDir.archiveDir.get().id
                val cache = activeDir.root.get().cache
                val archive = cache.index(indexId).archive(archiveId)
                val file = archive?.add(clipboardModel.data.get())
                if (file != null) {
                    val meta = FileSystemViewMeta(file.id, FileSystemViewMeta.MetaType.FILE)
                    activeDir.activeNodes.add(meta)
                    dataGrid.selectionModel.select(meta)
                }
            }
        }
        separator()
        item("Save File ${it.id}").action {
            val fileChoose = fileSaver(it)
            val file = fileChoose.showSaveDialog(dataGrid.scene.window)
            val indexId = activeDir.indexDir.get().id
            val archiveId = activeDir.archiveDir.get().id
            val cache = activeDir.root.get().cache
            val data = cache.data(indexId, archiveId, it.id)
            if (data != null) {
                file.writeBytes(data)
            }
        }
        item("Replace File ${it.id}").action {
            val files = chooseFile(
                "Choose File",
                arrayOf(FileChooser.ExtensionFilter("File", "*")),
                mode = FileChooserMode.Single
            )
            if (files.isNotEmpty()) {
                val file = files[0]
                val indexId = activeDir.indexDir.get().id
                val archiveId = activeDir.archiveDir.get().id
                val cache = activeDir.root.get().cache
                cache.put(indexId, archiveId, it.id, file.readBytes())
            }
        }
        item("Delete File ${it.id}").action {
            val indexId = activeDir.indexDir.get().id
            val archiveId = activeDir.archiveDir.get().id
            val cache = activeDir.root.get().cache
            val result = confirmation(
                "Archive Deletion",
                "Are you sure you want to delete archive ${it.id}?",
                buttons = arrayOf(ButtonType.YES, ButtonType.NO)
            )
            if (result.result == ButtonType.YES) {
                val answer = confirmation(
                    "Backup Archive",
                    "Would you like to backup the archive before you delete it?",
                    buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                )
                if (answer.result == ButtonType.YES) {
                    val fileChoose = fileSaver(it)
                    val file = fileChoose.showSaveDialog(dataGrid.scene.window)
                    val data = cache.data(indexId, archiveId, it.id)
                    file.writeBytes(data ?: byteArrayOf())
                }
                cache.remove(indexId, archiveId, it.id)
                dataGrid.selectionModel.clearSelection()
                activeDir.activeNodes.remove(it)
            }
        }
    }

    private fun ContextMenu.addArchiveMenuItems(
        it: FileSystemViewMeta,
        dataGrid: DataGrid<FileSystemViewMeta>
    ) {
        dataGrid.contextmenu { menu("New") { addNewArchiveItem() } }
        menu("New") { addNewArchiveItem() }
        separator()
        item("Save Archive ${it.id}").action {
            val fileChoose = fileSaver(it)
            val file = fileChoose.showSaveDialog(dataGrid.scene.window)
            val indexId = activeDir.indexDir.get().id
            val cache = activeDir.root.get().cache
            file.writeBytes(cache.index(indexId).readArchiveSector(it.id)?.data ?: byteArrayOf())
        }
        item("Replace Archive ${it.id}").action {
            val choose = chooseFile(
                "Choose Archive",
                arrayOf(FileChooser.ExtensionFilter("Archive", "*")),
                mode = FileChooserMode.Single
            ) {
                this.initialDirectory = File(System.getProperty("user.home"))
            }
            if (choose.isNotEmpty()) {
                val file = choose[0]
                val data = file.readBytes()
                val indexId = activeDir.indexDir.get().id
                val cache = activeDir.root.get().cache
                val result = confirmation(
                    "Replace Archive ${it.id}",
                    "Are you sure you want to replace this archive?",
                    buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                )
                if (result.result == ButtonType.YES) {
                    cache.index(indexId).apply {
                        writeArchiveSector(it.id, data)
                    }
                }
            }
        }
        item("Delete Archive ${it.id}").action {
            val indexId = activeDir.indexDir.get().id
            val cache = activeDir.root.get().cache
            val result = confirmation(
                "Archive Deletion",
                "Are you sure you want to delete archive ${it.id}?",
                buttons = arrayOf(ButtonType.YES, ButtonType.NO)
            )
            if (result.result == ButtonType.YES) {
                val answer = confirmation(
                    "Backup Archive",
                    "Would you like to backup the archive before you delete it?",
                    buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                )
                if (answer.result == ButtonType.YES) {
                    val fileChoose = fileSaver(it)
                    val file = fileChoose.showSaveDialog(dataGrid.scene.window)
                    val data = cache.index(indexId).readArchiveSector(it.id)
                    file.writeBytes(data?.data ?: byteArrayOf())
                }
                cache.index(indexId).remove(it.id)
                dataGrid.selectionModel.clearSelection()
                activeDir.activeNodes.remove(it)
            }
        }
    }

    private fun fileSaver(it: FileSystemViewMeta): FileChooser {
        return FileChooser().apply {
            title = "Save Archive"
            initialDirectory = File(System.getProperty("user.home"))
            initialFileName = "Archive ${it.id}"
        }
    }

    /**
     * This function was introduced due to a weird bug, where I couldn't add the same menu
     * to two different context menus. So we create new menus and items ....
     */

    private fun Menu.addNewArchiveItem() = item("Archive").action {
        val indexId = activeDir.indexDir.get().id
        val cache = activeDir.root.get().cache
        val input = TextInputDialog(null).apply {
            this.title = "Enter Archive Name"
        }
        val result = input.showAndWait()
        val archive = if (result.isPresent && result.get().isNotEmpty()) {
            cache.index(indexId).add(result.get())
        } else {
            cache.index(indexId).add()
        }
        activeDir.activeNodes.add(FileSystemViewMeta(archive.id, FileSystemViewMeta.MetaType.ARCHIVE))
        cache.index(indexId).update()
        events log "Finished Saving archive ${archive.id} in index $indexId"
    }

    /**
     * This function was introduced due to a weird bug, where I couldn't add the same menu
     * to two different context menus. So we create new menus and items ....
     */

    private fun Menu.addNewFileItem() = item("File").action {
        val indexId = activeDir.indexDir.get().id
        val archiveId = activeDir.archiveDir.get().id
        val cache = activeDir.root.get().cache
        val archive = cache.index(indexId).archive(archiveId)
        if (archive != null) {
            val files = chooseFile(
                "Choose File",
                arrayOf(FileChooser.ExtensionFilter("File", "*")),
                mode = FileChooserMode.Multi
            )
            if (files.isNotEmpty()) {
                files.forEach {
                    val file = archive.add(it.readBytes())
                    activeDir.activeNodes.add(FileSystemViewMeta(file.id, FileSystemViewMeta.MetaType.FILE))
                }
                cache.index(indexId).update()
                events log "Finished Saving archive ${archive.id} in index $indexId"
            }
        }
    }

}
