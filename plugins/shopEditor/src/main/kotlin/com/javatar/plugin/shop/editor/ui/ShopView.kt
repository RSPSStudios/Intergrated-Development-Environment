package com.javatar.plugin.shop.editor.ui

import com.javatar.plugin.shop.editor.shop.currency.ShopCurrency
import com.javatar.plugin.shop.editor.ui.models.NpcSelectionModel
import com.javatar.plugin.shop.editor.ui.models.ShopCacheConfigModel
import com.javatar.plugin.shop.editor.ui.models.ShopModel
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.control.SpinnerValueFactory
import javafx.util.StringConverter
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ShopView : Fragment() {

    val shopModel: ShopModel by inject()
    val cacheModel: ShopCacheConfigModel by inject()
    val npcSelectionModel: NpcSelectionModel by inject()

    override val root = vbox {
        alignment = Pos.CENTER
        spacing = 10.0

        label("Currency")
        choicebox(shopModel.currency, ShopCurrency.values().toList()) {
            this.converter = object : StringConverter<ShopCurrency>() {
                override fun toString(currency: ShopCurrency): String {
                    return currency.name.toLowerCase().capitalize()
                }

                override fun fromString(string: String): ShopCurrency {
                    return ShopCurrency.valueOf(string.toUpperCase())
                }
            }
        }
        textfield(shopModel.npcOption)
        label("Sell Multiplier")
        spinner(true, shopModel.sellMultiplier) {
            valueFactory = NumberSpinnerValueFactory()
        }
        label("Buy Multiplier")
        spinner(true, shopModel.buyMultiplier) {
            valueFactory = NumberSpinnerValueFactory()
        }
        checkbox("Ironman", shopModel.ironman)
        checkbox("General Store", shopModel.generalStore)
        checkbox("Hide Buy", shopModel.hideBuy)
        checkbox("Unique per Npc", shopModel.uniquePerNpc)


        hbox {
            alignment = Pos.CENTER
            spacing = 10.0
            button("Add Npc").action {
                if (cacheModel.npcProvider.get() != null) {
                    npcSelectionModel.npcs.setAll(cacheModel.npcProvider.get().npcIds())
                }
                NpcSelectionView().openModal(block = true)
                if (npcSelectionModel.result.get() != -1) {
                    shopModel.npcs.add(npcSelectionModel.result.get())
                }
            }
            button("Remove Npc").action {
                val selected = npcSelectionModel.selectedNpc.get()
                if (selected != -1) {
                    shopModel.npcs.remove(selected)
                }
            }
        }
        listview<Int>(shopModel.npcs) {
            npcSelectionModel.selectedNpc.bind(selectionModel.selectedItemProperty())
            cellCache {
                if (cacheModel.npcProvider.get() != null) {
                    val npc = cacheModel.npcProvider.get().getDefinition(it)
                    label(npc.name)
                } else {
                    label("Npc $it")
                }
            }
        }
    }

    class NumberSpinnerValueFactory(min: Int = 0, max: Int = Int.MAX_VALUE) : SpinnerValueFactory<Number>() {
        val minProperty = SimpleIntegerProperty(min)
        val maxProperty = SimpleIntegerProperty(max)
        override fun decrement(steps: Int) {
            val value = value
            if ((value.toInt() - steps) < minProperty.get()) {
                valueProperty().set(minProperty.get())
            } else {
                valueProperty().set(value.toInt() - steps)
            }
        }

        override fun increment(steps: Int) {
            val value = value
            if ((value.toInt() + steps) > maxProperty.get()) {
                valueProperty().set(maxProperty.get())
            } else {
                valueProperty().set(value.toInt() + steps)
            }
        }
    }

}
