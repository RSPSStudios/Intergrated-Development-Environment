package com.javatar.plugin.definition.editor.ui.editor.items

import com.displee.cache.CacheLibrary
import com.javatar.api.ui.loadPluginFXML
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.osrs.definitions.sprites.ItemSpriteFactory
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor
import com.javatar.plugin.definition.editor.managers.ItemProvider
import com.javatar.plugin.definition.editor.managers.ModelProvider
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import com.javatar.plugin.definition.editor.managers.TextureProvider
import com.javatar.plugin.definition.editor.ui.editor.items.model.ItemDefinitionModel
import javafx.beans.property.SimpleObjectProperty
import javafx.embed.swing.SwingFXUtils
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.Accordion
import javafx.scene.control.ListView
import javafx.scene.control.MenuItem
import javafx.scene.layout.AnchorPane
import tornadofx.*

class ItemEditorView : Fragment("Old School Item Editor") {

    override val root: AnchorPane = loadPluginFXML("${OsrsDefinitionEditor.properties["pluginPath"]}/itemeditor.fxml")

    private val duplicateItem: MenuItem by fxid()
    private val deleteItem: MenuItem by fxid()
    private val packItem: MenuItem by fxid()

    private val itemPropertiesPane: Accordion by fxid()

    private val itemList: ListView<ItemDefinition> by fxid()

    private val itemConfigPane: AnchorPane by fxid()
    private val itemModelPane: AnchorPane by fxid()
    private val itemOptionsPane: AnchorPane by fxid()

    private val definitionModel: ItemDefinitionModel by inject()

    private val manager = OldSchoolDefinitionManager.items

    private val factory = ItemSpriteFactory()

    val cacheProperty = SimpleObjectProperty<CacheLibrary>()
    private val cache: CacheLibrary by cacheProperty

    init {
        duplicateItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)
        deleteItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)
        packItem.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)
        itemPropertiesPane.disableWhen(itemList.selectionModel.selectedItemProperty().isNull)

        itemList.cellFormat {
            graphic = hbox {
                spacing = 15.0
                alignment = Pos.CENTER
                val cache = cache
                val image = SwingFXUtils.toFXImage(
                    factory.createSprite(
                        ItemProvider(cache),
                        ModelProvider(cache),
                        SpriteProvider(cache),
                        TextureProvider(cache),
                        item.id,
                        1,
                        1,
                        3153952,
                        false
                    ), null
                )
                imageview(image)
                label(item.name)
            }
        }

        itemList.selectionModel.selectedItemProperty().onChange {
            if(it != null) {
                definitionModel.update(it)
            }
        }

        itemConfigPane.add<ItemConfigsFragment>()
        itemModelPane.add<ItemModelFragment>()
        itemOptionsPane.add<ItemOptionsFragment>()

        cacheProperty.addListener { _, _, new ->
            loadItems(new)
        }
    }

    private fun loadItems(cache: CacheLibrary) {
        val ids = cache.index(2).archive(10)?.fileIds() ?: intArrayOf()
        ids.forEach {
            val def = manager.loader.load(it, cache.data(2, 10, it))
            itemList.items.add(def)
        }
    }

    @FXML
    fun newItem() {

    }

    @FXML
    fun duplicateItem() {

    }

    @FXML
    fun packItem() {

    }

    @FXML
    fun deleteItem() {

    }



}