package com.javatar.plugin.definition.editor.fs.types.files

import com.javatar.api.fs.ArchiveType
import com.javatar.api.fs.FileType
import com.javatar.api.fs.JFile
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.http.Client
import com.javatar.api.http.StringBody
import com.javatar.api.ui.models.AccountModel
import com.javatar.osrs.definitions.Definition
import com.javatar.osrs.definitions.DefinitionManager
import com.javatar.osrs.definitions.DeserializeDefinition
import com.javatar.plugin.definition.editor.OsrsDefinitionEditor
import com.javatar.plugin.definition.editor.ui.GenericDefinitionTool
import javafx.scene.control.Alert
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.image.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tornadofx.Scope
import tornadofx.alert
import tornadofx.find

open class DefinitionFileType<T : Definition, L : DeserializeDefinition<T>>(
    override val archiveId: Int,
    override val indexId: Int,
    val manager: DefinitionManager<T, L>,
    val endpoint: String
) : ArchiveType, KoinComponent {

    val accountModel: AccountModel by inject()
    val client: Client by inject()

    override fun icon(file: JFile, root: RootDirectory): ImageView? {
        return null
    }

    override fun cache(jfiles: List<JFile>, root: RootDirectory) {
        jfiles.forEach {
            manager.getDefinition(it.id, it.read())
        }
    }

    override fun identifier(file: JFile, root: RootDirectory): String {
        return "${manager.getDefinition(file.id, file.read()).name} - ${file.id}"
    }

    override fun open(file: JFile, root: RootDirectory, editorPane: TabPane) {
        val def = manager.load(file.id, file.read())
        val data = OsrsDefinitionEditor.gson.toJson(def)
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
            client.post<ByteArray>("tools/osrs/$endpoint", StringBody(json), creds)
                .catch { alert(Alert.AlertType.ERROR, "Error Encoding", it.message) }
                .onEach { file.write(it) }
                .onCompletion {
                    root.cache.index(indexId).update()
                    alert(Alert.AlertType.INFORMATION, "Encode Request", "Finished Encoding data.")
                }
                .launchIn(CoroutineScope(Dispatchers.JavaFx))
        }
    }
}