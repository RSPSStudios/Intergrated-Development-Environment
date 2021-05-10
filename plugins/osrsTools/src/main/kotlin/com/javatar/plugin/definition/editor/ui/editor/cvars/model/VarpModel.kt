package com.javatar.plugin.definition.editor.ui.editor.cvars.model

import com.javatar.osrs.definitions.impl.VarpDefinition
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.ViewModel

class VarpModel : ViewModel() {

    val id = bind { SimpleIntegerProperty(this, "id", -1) }

    val type = bind { SimpleIntegerProperty(this, "type", 0) }

    fun commitVarp() : VarpDefinition {
        val def = VarpDefinition(id.get())
        def.type = type.get()
        return def
    }

}