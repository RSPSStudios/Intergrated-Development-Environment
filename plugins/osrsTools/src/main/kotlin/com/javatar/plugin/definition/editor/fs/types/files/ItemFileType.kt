package com.javatar.plugin.definition.editor.fs.types.files

import com.javatar.api.fs.FileType
import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.http.Client
import com.javatar.api.http.StringBody
import com.javatar.api.ui.models.AccountModel
import com.javatar.osrs.definitions.impl.ItemDefinition
import com.javatar.osrs.definitions.sprites.ItemSpriteFactory
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor.Companion.gson
import com.javatar.plugin.definition.editor.managers.ItemProvider
import com.javatar.plugin.definition.editor.managers.ModelProvider
import com.javatar.plugin.definition.editor.managers.SpriteProvider
import com.javatar.plugin.definition.editor.managers.TextureProvider
import com.javatar.plugin.definition.editor.ui.GenericDefinitionTool
import javafx.embed.swing.SwingFXUtils
import javafx.scene.control.Alert
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.javafx.JavaFx
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tornadofx.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

@KoinApiExtension
class ItemFileType : FileType, KoinComponent {
    override val indexId: Int = 2
    override val archiveId: Int = 10

    private val factory = ItemSpriteFactory()
    private val manager = OldSchoolDefinitionManager.items
    private val client: Client by inject()
    private val accountModel: AccountModel by inject()

    private val cachedIcons = mutableMapOf<Int, Image>()

    override fun open(file: JFile, root: RootDirectory, editorPane: TabPane) {
        val def = manager.load(file.id, file.read())
        val data = gson.toJson(def)
        val tool = find<GenericDefinitionTool>(Scope())
        tool.editorModel.json.set(data)
        tool.editorModel.fileNode.set(file)
        tool.editorModel.fileType.set(this)
        tool.editorModel.root.set(root)
        editorPane.tabs.add(Tab(def.name, tool.root))
    }

    override fun save(json: String, file: JFile, root: RootDirectory) {
        val creds = accountModel.activeCredentials.get()
        if(creds != null) {
            client.post<ByteArray>("tools/osrs/items", StringBody(json), creds)
                .catch { alert(Alert.AlertType.ERROR, "Error Encoding", it.message) }
                .onEach { file.write(it) }
                .onCompletion {
                    root.cache.index(2).update()
                    alert(Alert.AlertType.INFORMATION, "Encode Request", "Finished Encoding data.")
                }
                .launchIn(CoroutineScope(Dispatchers.JavaFx))
        }
    }

    override fun identifier(file: JFile, root: RootDirectory): String {
        return manager.getDefinition(file.id)?.name ?: "unknown"
    }

    override fun cache(jfiles: List<JFile>, root: RootDirectory) {
        jfiles.forEach {
            manager.load(it.id, it.read())
        }
    }

    override fun icon(file: JFile, root: RootDirectory): ImageView {
        if (cachedIcons.containsKey(file.id)) {
            return ImageView(cachedIcons[file.id])
        }
        val image = SwingFXUtils.toFXImage(
            factory.createSprite(
                ItemProvider(root.cache),
                ModelProvider(root.cache),
                SpriteProvider(root.cache),
                TextureProvider(root.cache),
                file.id,
                1,
                1,
                3153952,
                false
            ), null
        )

        cachedIcons[file.id] = image

        return ImageView(image)
    }
}
