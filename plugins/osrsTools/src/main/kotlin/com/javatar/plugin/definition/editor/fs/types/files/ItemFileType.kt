package com.javatar.plugin.definition.editor.fs.types.files

import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.osrs.definitions.loaders.ItemLoader
import com.javatar.osrs.definitions.sprites.ItemSpriteFactory
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.managers.ItemProvider
import com.javatar.plugin.definition.editor.managers.ModelProvider
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import com.javatar.plugin.definition.editor.managers.TextureProvider
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import kotlin.collections.mutableMapOf
import kotlin.collections.set

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

@KoinApiExtension
class ItemFileType : DefinitionFileType<ItemDefinition, ItemLoader>(10, 2, OldSchoolDefinitionManager.items, "items"), KoinComponent {
    private val factory = ItemSpriteFactory(null, null, null, null)
    private val cachedIcons = mutableMapOf<Int, Image>()

    override fun identifier(file: JFile, root: RootDirectory): String {
        if(manager[file.id] == null) {
            manager.load(file.id, file.read())
        }
        return manager.get(file.id)?.name ?: "Unknown"
    }

    override fun icon(file: JFile, root: RootDirectory): ImageView {
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
