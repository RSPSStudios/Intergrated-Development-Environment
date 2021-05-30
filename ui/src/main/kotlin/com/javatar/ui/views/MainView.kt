package com.javatar.ui.views

import com.displee.cache.CacheLibrary
import com.javatar.api.fs.directories.RootDirectory
import com.javatar.api.fs.extensions.ArchiveTypeExtension
import com.javatar.api.fs.extensions.FileTypeExtension
import com.javatar.api.fs.extensions.IndexTypeExtension
import com.javatar.api.http.Client
import com.javatar.api.http.UserInformation
import com.javatar.api.ui.MenuItemExtension
import com.javatar.api.ui.ToolTabExtension
import com.javatar.api.ui.events.logs.EventLogType
import com.javatar.api.ui.fs.QuickToolExtension
import com.javatar.api.ui.models.AccountModel
import com.javatar.api.ui.models.AccountSettingsModel
import com.javatar.api.ui.models.EventLogModel
import com.javatar.api.ui.utilities.contextmenu
import com.javatar.fs.FileTypeManager
import com.javatar.api.ui.notifications.NotificationCenter
import com.javatar.ui.models.*
import com.javatar.ui.views.account.AccountSettingsFragment
import com.javatar.ui.views.account.LoginDialog
import io.ktor.util.*
import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import org.controlsfx.control.Notifications
import tornadofx.*
import java.awt.Desktop
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainView : View("RuneScape Private Server Studios") {

    override val root: StackPane by fxml("main-view.fxml")
    val vboxRoot: VBox by fxid()

    val caches: TreeView<Pair<String, String>> by fxid()
    val editorView: AnchorPane by fxid()
    val menuBar: MenuBar by fxid()
    val pluginsMenu: Menu by fxid()
    val toolTabs: TabPane by fxid()
    val accountSettingsBtn: MenuItem by fxid()
    val latestEvent: Label by fxid()
    val eventDate: Label by fxid()

    val configModel: CacheConfigurationModel by di()
    val editorModel: EditorModel by di()

    val pluginRepository: PluginRepositoryModel by di()

    val titleModel: TitleModel by inject()

    val accountSettingsModel: AccountSettingsModel by inject()
    val events: EventLogModel by di()
    val accountModel: AccountModel by di()
    val client: Client by di()

    private val time: String
        get() = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    private val ncenter: NotificationCenter by di()

    init {
        val css = MainView::class.java.getResource("notifications.css")
        importStylesheet(css.toExternalForm())


        events.latestLog.onChange {
            if(it != null) {
                ncenter.owner(root)
                ncenter.show(it)
            }
        }

        root.autosize()

        initEventLogBindings()

        initTitleBindings()

        initPlugins()

        editorView.add(editorModel.editorPane)

        caches.cellFormat {
            text = item.first
            if (item.first == "Caches") {
                contextmenu {
                    item("Open Cache").action {
                        loadCache()
                    }
                }
            } else {
                contextmenu {
                    item("Load").action {
                        editorModel.openFileExplorer(item.first, RootDirectory(CacheLibrary.create(item.second)))
                        events log "Loaded Cache ${item.first}."
                    }
                    menu("Load with") {
                        pluginRepository.manager.plugins.forEach { it ->
                            item(it.pluginId).action {
                                val typeManager = FileTypeManager()
                                pluginRepository.manager.getExtensions(FileTypeExtension::class.java).forEach {
                                    typeManager.registerFileType(it.createFileType())
                                }
                                pluginRepository.manager.getExtensions(ArchiveTypeExtension::class.java).forEach {
                                    typeManager.registerArchiveType(it.createArchiveType())
                                }
                                pluginRepository.manager.getExtensions(IndexTypeExtension::class.java).forEach {
                                    typeManager.registerIndexType(it.createIndexType())
                                }
                                editorModel.openFileExplorer(item.first, RootDirectory(CacheLibrary.create(item.second)), typeManager, it.pluginId)
                                events log "Loaded Cache ${item.first} with ${it.pluginId}"
                            }
                        }
                    }
                    separator()
                    menu("Quick Tools") {
                        pluginRepository.manager.getExtensions(QuickToolExtension::class.java).forEach {
                            it.applyQuickTool(this, item.second)
                        }
                    }
                    separator()
                    item("Remove").action {
                        configModel.cachePaths.remove(item.first)
                        configModel.save()
                        caches.root.children.remove(treeItem)
                    }
                }
            }
        }

        caches.root = TreeItem("Caches" to "")

        caches.rootProperty().bind(Bindings.createObjectBinding({
            TreeItem("Caches" to "").also {
                it.children.setAll(configModel.cachePaths.map { entry ->
                    TreeItem(entry.toPair())
                })
            }
        }, configModel.cachePaths))

        accountSettingsBtn.enableWhen(accountModel.loggedIn.or(accountModel.activeCredentials.isNotNull))

        events.log("Ready", EventLogType.FILTERED)
    }

    private fun initPlugins() {
        pluginRepository.manager.getExtensions(MenuItemExtension::class.java)
            .forEach { it.createMenuItem(pluginsMenu, menuBar) }

        pluginRepository.manager.getExtensions(ToolTabExtension::class.java)
            .forEach {
                it.createToolTab(toolTabs)
            }
    }

    private fun initTitleBindings() {
        titleProperty.bind(Bindings.createStringBinding({
            "${titleModel.title.get()} - ${titleModel.accountEmailOrName.get()}"
        }, titleModel.title, titleModel.accountEmailOrName))


        titleModel.accountEmailOrName.bind(Bindings.createStringBinding({
            if (accountModel.email.isNull.get()) {
                "Guest"
            } else accountModel.email.get()
        }, accountModel.email))
    }

    private fun initEventLogBindings() {
        latestEvent.textProperty().bind(Bindings.createStringBinding({
            events.latestLog.get()?.displayMessage ?: ""
        }, events.latestLog))
        eventDate.textProperty().bind(Bindings.createObjectBinding({
            "${events.latestLog.get()?.timestap ?: time}:"
        }, events.latestLog))
        eventDate.fontProperty().bind(latestEvent.fontProperty())

        latestEvent.fontProperty().bind(Bindings.createObjectBinding({
            events.latestLog.get()?.font ?: Font.font(14.0)
        }, events.latestLog))

        latestEvent.textFillProperty().bind(Bindings.createObjectBinding({
            events.latestLog.get()?.color ?: Color.WHITE
        }, events.latestLog))
    }

    @FXML
    fun loadCache() {
        val dir = chooseDirectory("Choose Cache Directory")
        if (dir != null && dir.exists()) {
            val nameDialog = TextInputDialog(dir.nameWithoutExtension)
            nameDialog.contentText = "Enter cache name"
            nameDialog.title = "Open Cache"
            nameDialog.headerText = "Please enter a name for the cache"
            configModel.cacheName.bind(nameDialog.editor.textProperty())
            configModel.cacheName.addValidator(nameDialog.editor) {
                if (caches.root.children.find { it.value.first == nameDialog.editor.text } != null) {
                    error("Cache already opened.")
                } else success("Cache successfully opened.")
            }
            val result = nameDialog.showAndWait()
            if (result.isPresent) {
                configModel.cachePaths[result.get()] = dir.absolutePath
                configModel.save()
            }
        } else {
            alert(Alert.AlertType.ERROR, "Could not find cache.")
        }
    }

    @InternalAPI
    @FXML
    fun pluginRepo() {
        find<PluginRepository>().openModal()
    }

    @FXML
    fun openPluginLocation() {
        val location = config.string("pf4j.pluginsDir", System.getProperty("pf4j.pluginsDir"))
        val path = Path.of(location)
        if (!Files.exists(path)) {
            Files.createDirectory(path)
        }
        if (Desktop.isDesktopSupported()) {
            GlobalScope.launch(Dispatchers.IO) {
                val desktop = Desktop.getDesktop()
                desktop.browse(File(location).toURI())
            }
        }
    }

    @FXML
    fun setPluginLocation() {
        val dir = chooseDirectory("Choose plugin location")
        with(config) {
            if (dir != null && dir.exists() && dir.isDirectory) {
                set("pf4j.pluginsDir", dir.absolutePath)
                save()
            }
        }
    }

    @FXML
    fun openPreferences() {
        PreferencesView().openModal(block = true)
    }

    @FXML
    fun login() {
        LoginDialog().openModal(block = true)
    }

    @InternalAPI
    @FXML
    fun accountSettings() {
        client.get<UserInformation>("user/info", accountModel.activeCredentials.get())
            .onEach {
                accountSettingsModel.primaryEmail.set(it.primaryEmail)
                accountSettingsModel.subscribed.set(if (it.hasToken) "Yes" else "No")
            }
            .launchIn(CoroutineScope(Dispatchers.JavaFx))
        AccountSettingsFragment().openModal(block = true)
    }

    @FXML
    fun doNotification() {
        /*val nscope = Scope()
        val frag = find<NotificationFragment>(nscope)
        frag.model.eventLog.set(EventLog("This is a test message", ""))
        frag.root.layoutX = 975.0
        frag.root.layoutY = 572.0
        root.add(frag)*/
        Notifications.create().apply {
            title("Test Notification")
            text("This is a notification.")
            owner(root)
            darkStyle()
        }.show()
    }

    @FXML
    fun reloadCss() {
        root.scene?.reloadStylesheets()
    }
}
