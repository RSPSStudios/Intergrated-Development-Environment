package com.javatar.plugin.shop.editor.ui

import com.displee.cache.CacheLibrary
import com.javatar.plugin.shop.editor.DefinitionManager.gson
import com.javatar.plugin.shop.editor.shop.Shop
import com.javatar.plugin.shop.editor.shop.ShopItem
import com.javatar.plugin.shop.editor.shop.currency.ShopCurrency
import com.javatar.plugin.shop.editor.ui.models.*
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import tornadofx.*
import java.io.File

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class ShopEditorView : Fragment() {

    override val root: BorderPane by fxml("shop-editor.fxml")

    val cacheModel: ShopCacheConfigModel by inject()
    val shopConfigModel: ShopConfigModel by inject()
    val itemSelectModel: ItemSelectionModel by inject()
    val preferences: PreferenceModel by inject()
    val shopModel: ShopModel by inject()

    val shopList: ListView<ShopModel> by fxid()

    val cachePath: TextField by fxid()

    val shopView: HBox by fxid()

    val addItemBtn: Button by fxid()
    val removeItemBtn: Button by fxid()
    val newShopBtn: Button by fxid()
    val deleteShopBtn: Button by fxid()
    val renameShopBtn: Button by fxid()

    val shopName: TextField by fxid()

    init {

        addItemBtn.disableWhen(shopList.selectionModel.selectedItemProperty().isNull)
        removeItemBtn.disableWhen(shopList.selectionModel.selectedItemProperty().isNull)
        deleteShopBtn.disableWhen(shopList.selectionModel.selectedItemProperty().isNull)
        renameShopBtn.disableWhen(shopList.selectionModel.selectedItemProperty().isNull)

        shopName.textProperty().bindBidirectional(shopModel.name)

        shopList.disableWhen(shopConfigModel.shopFilesPath.isNull)
        shopView.disableWhen(shopConfigModel.shopFilesPath.isNull)

        shopList.itemsProperty().bind(shopModel.shops)

        shopList.cellCache {
            vbox {
                label(it.name.get())
                label("Items: ${it.items.size}")
                label("Currency: ${it.currency.get().name.toLowerCase().capitalize()}")
            }
        }

        shopList.selectionModel.selectedItemProperty().onChange {
            if (it != null) {
                shopModel.items.set(FXCollections.observableArrayList(it.items))
                shopModel.name.set(it.name.get())
                shopModel.currency.set(it.currency.get())
                shopModel.npcs.set(it.npcs.toList().toObservable())
                shopModel.buyMultiplier.set(it.buyMultiplier.get())
                shopModel.sellMultiplier.set(it.sellMultiplier.get())
                shopModel.ironman.set(it.ironman.get())
                shopModel.generalStore.set(it.generalStore.get())
                shopModel.uniquePerNpc.set(it.uniquePerNpc.get())
            } else {
                shopModel.items.clear()
                shopModel.name.set("")
                shopModel.currency.set(ShopCurrency.COINS)
                shopModel.npcs.clear()
                shopModel.buyMultiplier.set(0)
                shopModel.sellMultiplier.set(0)
                shopModel.ironman.set(false)
                shopModel.generalStore.set(false)
                shopModel.uniquePerNpc.set(false)
            }
        }

        shopView.spacing = 10.0
        shopView.add(ShopItemView())
        shopView.add(ShopView())

        shopConfigModel.shopFilesPath.onChange { path ->
            if (path != null) {
                val dirChooser = File(path)
                val files = dirChooser.listFiles()
                if (files != null && files.isNotEmpty()) {
                    shopModel.shops.setAll(
                        files.filterNot { it.isDirectory }.map { gson.fromJson(it.readText(), Shop::class.java) }
                            .map { ShopModel(it) }
                    )
                }
            }
        }

        if (shopConfigModel.shopFilesPath.get() != null) {
            val it = shopConfigModel.shopFilesPath.get()
            if (it != null) {
                val dirChooser = File(it)
                val files = dirChooser.listFiles()
                if (files != null && files.isNotEmpty()) {
                    shopModel.shops.setAll(
                        files.filterNot { it.isDirectory }.map { gson.fromJson(it.readText(), Shop::class.java) }
                            .map { ShopModel(it) }
                    )
                }
            }
        }

        cachePath.textProperty().bind(Bindings.createStringBinding({
            cacheModel.cache.get()?.path ?: ""
        }, cacheModel.cache))

    }

    @FXML
    fun addItem() {
        if (cacheModel.itemProvider.get() != null) {
            itemSelectModel.items.setAll(cacheModel.itemProvider.get().itemIds())
        }
        ItemSelectionView().openModal(block = true)
        if (itemSelectModel.result.get() != -1) {
            shopModel.items.add(ShopItemModel(ShopItem(itemSelectModel.result.get())))
        }
    }

    @FXML
    fun removeItem() {
        val selectedItem = shopModel.editingItem.get()
        if (selectedItem != null) {
            val provider = cacheModel.itemProvider.get()
            val name =
                if (provider != null) provider.getDefinition(selectedItem.itemId.get()).name else "${selectedItem.itemId}"

            val result = if (preferences.warnOnDelete.get()) {
                confirmation(
                    "Delete $name",
                    "Are you sure you want to delete this item?",
                    buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                ).result
            } else ButtonType.YES
            if (result == ButtonType.YES) {
                shopModel.items.remove(selectedItem)
            }
        }
    }

    @FXML
    fun renameShop() {
        if (!shopModel.name.isBound) {
            shopModel.name.set(shopName.text)
        }
    }

    @FXML
    fun newShop() {
        val shopModel = ShopModel()
        this.shopModel.shops.add(shopModel)
        this.shopList.selectionModel.select(shopModel)
        this.shopList.scrollTo(shopModel)
    }

    @FXML
    fun deleteShop() {
        val selected = this.shopList.selectedItem
        if (selected != null) {
            val result = if (preferences.warnOnDelete.get()) {
                confirmation(
                    "Delete Shop ${selected.name.get()}",
                    "Are you sure you want to delete this shop?",
                    buttons = arrayOf(ButtonType.YES, ButtonType.NO)
                ).result
            } else ButtonType.YES
            if (result == ButtonType.YES) {
                shopModel.shops.remove(selected)
            }
        }
    }

    @FXML
    fun loadShops() {
        val dirChooser = chooseDirectory("Choose Shops Directory")
        println(dirChooser != null)
        println(dirChooser?.isDirectory)
        if (dirChooser != null && dirChooser.exists() && dirChooser.isDirectory) {
            shopConfigModel.shopFilesPath.set(dirChooser.absolutePath)
            shopConfigModel.commit()
        }
    }

    @FXML
    fun loadCache() {
        val dirChooser = chooseDirectory("Choose Cache Directory")
        if (dirChooser != null && dirChooser.exists() && dirChooser.isDirectory) {
            val cache = CacheLibrary.create(dirChooser.absolutePath)
            cachePath.text = cache.path
            cacheModel.cache.set(cache)
            cacheModel.commit()
        }
    }

}