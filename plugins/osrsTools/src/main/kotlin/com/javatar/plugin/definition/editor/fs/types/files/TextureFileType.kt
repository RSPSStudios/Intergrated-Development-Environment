package com.javatar.plugin.definition.editor.fs.types.files

import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.osrs.definitions.impl.TextureDefinition
import com.javatar.osrs.definitions.loaders.TextureLoader
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import javafx.scene.image.ImageView
import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
import java.nio.IntBuffer

class TextureFileType : DefinitionFileType<TextureDefinition, TextureLoader>(0, 9, OldSchoolDefinitionManager.textures, "textures") {

    override fun icon(file: JFile, root: RootDirectory): ImageView? {
        val provider = SpriteProvider(root.cache)
        val def = manager.getDefinition(file.id, file.read())
        if(def.fileIds.isNotEmpty()) {
            val spriteId = def.fileIds[0]
            val spriteGroup = provider.getDefinition(spriteId)
            val sprite = spriteGroup.sprites[0]
            val writableImage = WritableImage(sprite.width, sprite.height)
            writableImage.pixelWriter.setPixels(
                0,
                0,
                sprite.width,
                sprite.height,
                PixelFormat.getIntArgbInstance(),
                IntBuffer.wrap(sprite.pixels),
                0
            )
            return ImageView(writableImage)
        }
        return super.icon(file, root)
    }
}