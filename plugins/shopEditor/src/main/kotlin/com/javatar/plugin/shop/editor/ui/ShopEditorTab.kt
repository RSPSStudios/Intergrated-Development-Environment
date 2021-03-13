package com.javatar.plugin.shop.editor.ui

import com.javatar.api.ui.ToolTabExtension
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import org.pf4j.Extension
import tornadofx.find

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

@Extension
class ShopEditorTab : ToolTabExtension {
    override fun createToolTab(toolTabPane: TabPane) {
        val tab = Tab("Shop Editor", find<ShopEditorView>().root)
        tab.isClosable = false
        toolTabPane.tabs.add(tab)
    }
}
