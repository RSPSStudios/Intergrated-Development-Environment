package com.javatar.plugin.definition.editor.fs.types.files

import com.javatar.api.fs.FileType
import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.http.Client
import com.javatar.api.http.StringBody
import com.javatar.api.ui.models.AccountModel
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.osrs.definitions.loaders.ItemLoader
import com.javatar.osrs.definitions.sprites.ItemSpriteFactory
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import com.javatar.plugin.definition.editor.managers.ItemProvider
import com.javatar.plugin.definition.editor.managers.ModelProvider
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import com.javatar.plugin.definition.editor.managers.TextureProvider
import com.javatar.plugin.definition.editor.ui.GenericDefinitionTool
import javafx.embed.swing.SwingFXUtils
import javafx.scene.control.Alert
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.javafx.JavaFx
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

@KoinApiExtension
class ItemFileType : DefinitionFileType<ItemDefinition, ItemLoader>(10, 2, OldSchoolDefinitionManager.items, "items"), KoinComponent {
    private val factory = ItemSpriteFactory()
    private val cachedIcons = mutableMapOf<Int, Image>()

    override fun identifier(file: JFile, root: RootDirectory): String {
        return manager.getDefinition(file.id, file.read()).name
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
