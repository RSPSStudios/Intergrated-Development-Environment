package com.javatar.plugin.definition.editor.fs.types.archives

import com.javatar.api.fs.ArchiveType
import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.fromJson
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import javafx.scene.image.ImageView

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ModelArchiveType : ArchiveType {
    override val indexId: Int = 7
    override val archiveId: Int = -1

    val models = OldSchoolDefinitionManager.models

    override fun open(file: JFile, root: RootDirectory): String {
        return gson.toJson(models.load(file.id, file.read()))
    }

    override fun save(data: String, file: JFile, root: RootDirectory) {
        file.write(models.save(gson.fromJson(data)))
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
