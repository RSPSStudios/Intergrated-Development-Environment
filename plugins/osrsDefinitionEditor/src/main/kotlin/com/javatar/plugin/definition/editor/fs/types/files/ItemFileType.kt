package com.javatar.plugin.definition.editor.fs.types.files

import com.javatar.api.fs.FileType
import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import com.javatar.plugin.definition.editor.managers.ItemProvider
import com.javatar.plugin.definition.editor.managers.ModelProvider
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import com.javatar.plugin.definition.editor.managers.TextureProvider
import com.javatar.sprites.ItemSpriteFactory
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import javafx.scene.image.ImageView

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class ItemFileType : FileType {
    override val indexId: Int = 2
    override val archiveId: Int = 10

    private val factory = ItemSpriteFactory()
    private val manager = OldSchoolDefinitionManager.items

    private val cachedIcons = mutableMapOf<Int, Image>()

    override fun open(file: JFile, root: RootDirectory): String {
        return gson.toJson(manager.load(file.id, file.read()))
    }

    override fun save(data: String, file: JFile, root: RootDirectory) {
        file.write(manager.save(gson.fromJson(data, ItemDefinition::class.java)))
    }

    override fun identifier(file: JFile, root: RootDirectory): String {
        return manager.getDefinition(file.id)?.name ?: "unknown"
    }

    override fun cache(jfiles: List<JFile>, root: RootDirectory) {
        jfiles.forEach {
            manager.load(it.id, it.read())
        }
    }

    override fun icon(file: JFile, root: RootDirectory): ImageView? {
        if (cachedIcons.containsKey(file.id)) {
            return ImageView(cachedIcons[file.id])
        }
        val image = SwingFXUtils.toFXImage(
            factory.createSprite(
                ItemProvider(root.cache),
                ModelProvider(root.cache),
                SpriteProvider(root.cache),
                TextureProvider(root.cache),
                file.id,
                1,
                1,
                3153952,
                false
            ), null
        )

        cachedIcons[file.id] = image

        return ImageView(image)
    }
}
