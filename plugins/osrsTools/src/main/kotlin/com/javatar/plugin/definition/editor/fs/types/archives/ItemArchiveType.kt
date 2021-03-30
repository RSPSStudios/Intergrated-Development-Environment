package com.javatar.plugin.definition.editor.fs.types.archives

import com.javatar.api.fs.ArchiveType
import com.javatar.api.fs.FileType
import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import javafx.scene.control.TabPane
import javafx.scene.image.ImageView

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ItemArchiveType : ArchiveType {
    override val indexId: Int = 2
    override val archiveId: Int = 10

    override fun open(file: JFile, root: RootDirectory, editorTab: TabPane) {
    }

    override fun save(json: String, file: JFile, root: RootDirectory) {
    }

    override fun identifier(file: JFile, root: RootDirectory): String {
        return "Items"
    }

    override fun cache(jfiles: List<JFile>, root: RootDirectory) {

    }

    override fun icon(file: JFile, root: RootDirectory): ImageView? {
        return null
    }
}
