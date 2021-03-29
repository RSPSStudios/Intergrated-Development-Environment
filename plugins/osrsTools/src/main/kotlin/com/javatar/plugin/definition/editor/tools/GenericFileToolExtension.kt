package com.javatar.plugin.definition.editor.tools

import com.javatar.api.ui.FileTypeToolExtension
import com.javatar.plugin.definition.editor.ui.GenericDefinitionTool
import org.pf4j.Extension
import tornadofx.Fragment
import tornadofx.find

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

@Extension
class GenericFileToolExtension : FileTypeToolExtension {
    override fun createToolModal(): Fragment {
        return find<GenericDefinitionTool>()
    }
}
