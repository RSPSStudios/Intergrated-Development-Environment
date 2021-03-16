package com.javatar.plugin.shop.editor.ui

import com.javatar.api.ui.utilities.bindSelected
import com.javatar.api.ui.utilities.datagrid
import com.javatar.api.ui.utilities.dynamicContent
import com.javatar.plugin.shop.editor.ui.models.PreferenceModel
import com.javatar.plugin.shop.editor.ui.models.ShopCacheConfigModel
import com.javatar.plugin.shop.editor.ui.models.ShopItemModel
import com.javatar.plugin.shop.editor.ui.models.ShopModel
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.binding.Bindings
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Pos
import javafx.scene.image.ImageView
import tornadofx.Fragment
import tornadofx.label
import tornadofx.spinner
import tornadofx.vbox

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

class ShopItemView : Fragment() {

    val shopModel: ShopModel by inject()
    val cacheModel: ShopCacheConfigModel by inject()
    val prefModel: PreferenceModel by inject()

    override val root = datagrid<ShopItemModel>(shopModel.items) {

        prefWidth = 698.0
        prefHeight = 698.0

        cellWidth = 200.0
        cellHeight = 200.0

        bindSelected(shopModel.editingItem)

        cellCache { shopItem ->
            vbox {
                alignment = Pos.CENTER
                spacing = 10.0

                val box = vbox {
                    alignment = Pos.CENTER
                    dynamicContent(cacheModel.itemProvider, prefModel.disableIcons) {
                        if (!prefModel.disableIcons.get() && cacheModel.itemProvider.get() != null) {
                            val view = ImageView()
                            view.imageProperty().bind(Bindings.createObjectBinding({
                                SwingFXUtils.toFXImage(
                                    cacheModel.spriteFactory.get()
                                        .createSprite(
                                            cacheModel.itemProvider.get(),
                                            cacheModel.modelProvider.get(),
                                            cacheModel.spriteProvider.get(),
                                            cacheModel.textureProvider.get(),
                                            shopItem.itemId.get(),
                                            1,
                                            1,
                                            3153952,
                                            false
                                        ),
                                    null
                                )
                            }, shopItem.itemId))
                            this.children.setAll(view)
                        } else this.children.setAll(FontAwesomeIconView(FontAwesomeIcon.FILE).also { icon ->
                            icon.glyphSize = 64
                        })
                    }
                }
                add(box)

                label {
                    textProperty().bind(Bindings.createStringBinding({
                        if (cacheModel.itemProvider.get() != null) {
                            cacheModel.itemProvider.get().getDefinition(shopItem.itemId.get()).name
                        } else {
                            "Item ${shopItem.itemId.get()}"
                        }
                    }, cacheModel.itemProvider, shopItem.itemId))
                }
                spinner(
                    editable = true,
                    valueFactory = ShopView.NumberSpinnerValueFactory(),
                    property = shopItem.itemId
                )
            }
        }

        selectionTrigger { it.clickCount == 1 }
    }

}
