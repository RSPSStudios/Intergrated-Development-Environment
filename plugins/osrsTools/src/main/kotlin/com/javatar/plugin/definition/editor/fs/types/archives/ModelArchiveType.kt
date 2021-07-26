package com.javatar.plugin.definition.editor.fs.types.archives

import com.javatar.api.fs.ArchiveType
import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.osrs.definitions.loaders.ModelLoader
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager
import javafx.scene.control.TabPane
import javafx.scene.image.ImageView

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ModelArchiveType : ArchiveType {
    override val indexId: Int = 7
    override val archiveId: Int = -1

    val models = ConfigDefinitionManager(ModelLoader())

    override fun open(file: JFile, root: RootDirectory, editorTab: TabPane) {

    }

    override fun save(json: String, file: JFile, root: RootDirectory) {
    }

    override fun identifier(file: JFile, root: RootDirectory): String {
        return "Model"
    }

    override fun cache(jfiles: List<JFile>, root: RootDirectory) {
        jfiles.forEach {
            models.load(it.id, it.read())
        }
    }

    override fun icon(file: JFile, root: RootDirectory): ImageView? {
        return null
    }
}
