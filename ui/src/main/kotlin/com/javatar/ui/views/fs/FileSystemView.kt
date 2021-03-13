package com.javatar.ui.views.fs

import com.javatar.fs.directories.ArchiveDirectory
import com.javatar.fs.directories.IndexDirectory
import com.javatar.ui.datagrid
import com.javatar.ui.models.ActiveDirectoryModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.collections.FXCollections
import javafx.geometry.Pos
import tornadofx.Fragment
import tornadofx.label
import tornadofx.vbox

class FileSystemView : Fragment() {

    override val scope: FileExplorerScope = super.scope as FileExplorerScope

    val activeDir: ActiveDirectoryModel = scope.activeDirectoryModel

    override val root = vbox {

        datagrid<FileSystemViewMeta> {
            itemsProperty.bind(activeDir.activeNodes)
            prefWidth = 830.0
            prefHeight = 705.0

            singleSelect = true

            cellCache {
                vbox {
                    spacing = 10.0
                    alignment = Pos.CENTER
                    when (it.meta) {
                        FileSystemViewMeta.MetaType.INDEX -> {
                            add(FontAwesomeIconView(FontAwesomeIcon.FOLDER).also { it.glyphSize = 64 })
                            label("Index ${it.id}")
                        }
                        FileSystemViewMeta.MetaType.ARCHIVE -> {
                            add(FontAwesomeIconView(FontAwesomeIcon.FOLDER).also { it.glyphSize = 64 })
                            label("Archive ${it.id}")
                        }
                        FileSystemViewMeta.MetaType.FILE -> {
                            add(FontAwesomeIconView(FontAwesomeIcon.FILE).also { it.glyphSize = 64 })
                            label("File ${it.id}")
                        }
                    }
                }
            }

            selectionTrigger("action") { it.clickCount == 2 }
            selectionTrigger { it.clickCount == 1 }
            onSelectionChange("action") { meta ->
                when (meta.meta) {
                    FileSystemViewMeta.MetaType.INDEX -> {
                        val indexNode = activeDir.root.get().nodes().find { it.nodeIndex == meta.id }
                        if (indexNode != null) {
                            val metaNodes =
                                indexNode.nodes().map { FileSystemViewMeta(it.id, FileSystemViewMeta.MetaType.ARCHIVE) }
                            activeDir.indexDir.set(IndexDirectory(indexNode.nodeIndex, indexNode.parent))
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
                                activeDir.archiveDir.set(ArchiveDirectory(archiveNode.id, archiveNode.parent))
                                activeDir.activeNodes.set(FXCollections.observableList(metaNodes))
                                selectionModel.clearSelection()
                            }
                        }
                    }
                    FileSystemViewMeta.MetaType.FILE -> {

                    }
                }
            }

        }
    }

}
