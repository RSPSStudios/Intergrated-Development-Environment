package com.javatar.plugin.definition.editor.fs.types.archives

import com.javatar.api.fs.ArchiveType
import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import javafx.scene.control.TabPane
import javafx.scene.image.ImageView

class AnimationArchiveType(override val archiveId: Int = 12, override val indexId: Int = 2) : ArchiveType {
    override fun cache(jfiles: List<JFile>, root: RootDirectory) {
    }

    override fun icon(file: JFile, root: RootDirectory): ImageView? {
        return null
    }

    override fun identifier(file: JFile, root: RootDirectory): String {
        return "Animations"
    }

    override fun open(file: JFile, root: RootDirectory, editorPane: TabPane) {
    }

    override fun save(json: String, file: JFile, root: RootDirectory) {
    }
}