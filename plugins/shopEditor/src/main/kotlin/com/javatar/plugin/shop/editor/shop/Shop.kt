package com.javatar.plugin.shop.editor.shop

import com.javatar.plugin.shop.editor.shop.currency.ShopCurrency

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class Shop(
    val name: String,
    val currency: ShopCurrency = ShopCurrency.COINS,
    val ironman: Boolean = false,
    val generalStore: Boolean = false,
    val items: Array<ShopItem> = arrayOf(),
    val npcs: IntArray = intArrayOf(),
    val npcOption: String = "trade",
    val sellMultiplier: Int = 1,
    val buyMultiplier: Int = 1,
    val hideBuy: Boolean = false,
    val uniquePerNpc: Boolean = false
)
