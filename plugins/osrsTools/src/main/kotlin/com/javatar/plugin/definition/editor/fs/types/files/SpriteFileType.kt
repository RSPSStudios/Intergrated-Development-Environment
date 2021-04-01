package com.javatar.plugin.definition.editor.fs.types.files

import com.javatar.api.fs.JDirectory
import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.osrs.definitions.impl.SpriteGroupDefinition
import com.javatar.osrs.definitions.loaders.SpriteLoader
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import com.javatar.plugin.definition.editor.managers.TextureProvider
import com.sun.javafx.iio.ImageStorage
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.ImageView
import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
import java.awt.image.BufferedImage
import java.nio.IntBuffer

class SpriteFileType : DefinitionFileType<SpriteGroupDefinition, SpriteLoader>(-1, 8, OldSchoolDefinitionManager.sprites, "sprites") {
    override fun identifier(file: JFile, root: RootDirectory): String {
        return "Sprite Group"
    }

    override fun icon(archive: JDirectory<JFile>, root: RootDirectory): ImageView? {
        val provider = SpriteProvider(root.cache)
        val group = provider.getDefinition(archive.id)
        if(group.sprites.isNotEmpty() && group.sprites.size == 1) {
            val sprite = group.sprites[0]
            if(sprite.width <= 0 || sprite.height <= 0) {
                return null
            }
            val image = BufferedImage(sprite.width, sprite.height, BufferedImage.TYPE_INT_ARGB)
            image.setRGB(0, 0, sprite.width, sprite.height, sprite.pixels, 0, sprite.width)
            return ImageView(SwingFXUtils.toFXImage(image, null))
        }
        return super.icon(archive, root)
    }

}