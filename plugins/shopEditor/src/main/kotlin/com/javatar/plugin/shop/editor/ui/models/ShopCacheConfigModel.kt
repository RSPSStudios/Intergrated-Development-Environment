package com.javatar.plugin.shop.editor.ui.models

import com.displee.cache.CacheLibrary
import com.javatar.osrs.definitions.sprites.ItemSpriteFactory
import com.javatar.plugin.shop.editor.providers.*
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ShopCacheConfigModel : ViewModel() {

    val cache = bind {
        SimpleObjectProperty<CacheLibrary>(this, "cache",
            config.string("CACHE")?.let { CacheLibrary.create(it) })
    }

    val itemProvider =
        bind { SimpleObjectProperty<ItemProvider>(this, "item_provider", cache.get()?.let { ItemProvider(it) }) }
    val npcProvider =
        bind { SimpleObjectProperty<NpcProvider>(this, "npc_provider", cache.get()?.let { NpcProvider(it) }) }
    val spriteProvider =
        bind { SimpleObjectProperty<SpriteProvider>(this, "sprite_provider", cache.get()?.let { SpriteProvider(it) }) }
    val textureProvider = bind {
        SimpleObjectProperty<TextureProvider>(
            this,
            "texture_provider",
            cache.get()?.let { TextureProvider(it) })
    }
    val modelProvider =
        bind { SimpleObjectProperty<ModelProvider>(this, "model_provider", cache.get()?.let { ModelProvider(it) }) }
    val spriteFactory = bind { SimpleObjectProperty<ItemSpriteFactory>(this, "sprite_factory", cache.get()?.let {
        ItemSpriteFactory(ItemProvider(it), ModelProvider(it), SpriteProvider(it), TextureProvider(it))
    }) }

    override fun onCommit() {
        super.onCommit()
        with(config) {
            if (cache.get() != null) {
                set("CACHE", cache.get().path)
                save()
            }
        }
    }
}
