package com.javatar.plugin.shop.editor.ui.models

import com.javatar.plugin.shop.editor.shop.ShopItem
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.ItemViewModel

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 15 2021
 */

class ShopItemModel(item: ShopItem = ShopItem(0, 1, 1, 0)) : ItemViewModel<ShopItem>(item) {

    val itemId = bind { SimpleIntegerProperty(this, "item_id", item.itemId) }
    val stock = bind { SimpleIntegerProperty(this, "stock", item.stock) }
    val buyPrice = bind { SimpleIntegerProperty(this, "buy_price", item.buy_price) }
    val sellPrice = bind { SimpleIntegerProperty(this, "sell_price", item.sell_price) }

    override fun onCommit() {
        super.onCommit()
        item = ShopItem(itemId.get(), stock.get(), buyPrice.get(), sellPrice.get())
    }
}
