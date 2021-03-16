package com.javatar.plugin.shop.editor.ui.models

import com.javatar.plugin.shop.editor.shop.Shop
import javafx.beans.property.*
import javafx.collections.FXCollections
import tornadofx.ItemViewModel
import tornadofx.toObservable

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ShopModel(shop: Shop = Shop("new shop")) : ItemViewModel<Shop>(shop) {

    val shops = bind { SimpleListProperty<ShopModel>(this, "shops", FXCollections.observableArrayList()) }

    val items =
        bind { SimpleListProperty(this, "shop_items", shop.items.map { ShopItemModel(it) }.toList().toObservable()) }
    val name = bind { SimpleStringProperty(this, "name", shop.name) }
    val currency = bind { SimpleObjectProperty(this, "currency", shop.currency) }
    val ironman = bind { SimpleBooleanProperty(this, "ironman", shop.ironman) }
    val generalStore = bind { SimpleBooleanProperty(this, "general_store", shop.generalStore) }
    val npcs = bind { SimpleListProperty(this, "npcs", shop.npcs.toList().toObservable()) }
    val npcOption = bind { SimpleStringProperty(this, "npc_option", shop.npcOption) }
    val sellMultiplier = bind { SimpleIntegerProperty(this, "sell_multiplier", shop.sellMultiplier) }
    val buyMultiplier = bind { SimpleIntegerProperty(this, "buy_multiplier", shop.buyMultiplier) }
    val hideBuy = bind { SimpleBooleanProperty(this, "hide_buy", shop.hideBuy) }
    val uniquePerNpc = bind { SimpleBooleanProperty(this, "unique_per_npc", shop.uniquePerNpc) }

    val editingItem = bind { SimpleObjectProperty<ShopItemModel>(this, "editing_item") }

    override fun onCommit() {
        super.onCommit()
        if (name.get() != null) {
            items.forEach { it.commit() }
            this.item = Shop(
                name.get(),
                currency.get(),
                ironman.get(),
                generalStore.get(),
                items.map { it.item }.toTypedArray(),
                npcs.toIntArray(),
                npcOption.get(),
                sellMultiplier.get(),
                buyMultiplier.get(),
                hideBuy.get(),
                uniquePerNpc.get()
            )
        }
    }
}
