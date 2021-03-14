package com.javatar.plugin.definition.editor.fs.indices

import com.javatar.api.fs.IndexType
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.fs.extensions.IndexTypeExtension
import javafx.scene.image.ImageView
import org.pf4j.Extension

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */
@Extension
class ModelIndexTypeExtension : IndexTypeExtension {
    override fun createIndexType(): IndexType {
        return ModelIndexType()
    }

    private class ModelIndexType : IndexType {
        override val indexId: Int = 7
        override fun identifier(root: RootDirectory): String {
            return "Models"
        }

        override fun icon(root: RootDirectory): ImageView? {
            return null
        }
    }
}
