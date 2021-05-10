package com.javatar.plugin.definition.editor.ui.editor.cvars.model

import com.javatar.osrs.definitions.impl.VarbitDefinition
import com.javatar.osrs.tools.VariableTools.bitCount
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel
import tornadofx.onChange

class VarbitModel : ViewModel() {

    val id = bind { SimpleIntegerProperty(this, "id", -1) }

    val varpId = bind { SimpleIntegerProperty(this, "varp_id", 0) }

    val lsb = bind { SimpleIntegerProperty(this, "lsb", 0) }

    val msb = bind { SimpleIntegerProperty(this, "msb", 0) }

    val maxValue = bind { SimpleIntegerProperty(this, "max_value", 0) }

    val variableType = bind { SimpleObjectProperty(this, "variable_type", VariableType.BOOLEAN) }

    init {
        variableType.onChange {
            if(it != null && it !== variableType.get()) {
                val type = variableType.get()
                when {
                    type === VariableType.BOOLEAN -> {
                        msb.set(lsb.get())
                    }
                    type === VariableType.CUSTOM -> {
                        val bitOffset = maxValue.get().bitCount() - 1
                        msb.set(lsb.get() + bitOffset)
                    }
                    else -> {
                        msb.set(lsb.get() + type.bitSize)
                    }
                }
            }
        }
        maxValue.onChange {
            val type = variableType.get()
            if(type != null && type === VariableType.CUSTOM) {
                val bitOffset = it.bitCount()
                msb.set(lsb.get() + bitOffset)
            }
        }
    }

    fun commitVarbit() : VarbitDefinition {
        val def = VarbitDefinition(id.get())
        def.index = varpId.get()
        def.leastSignificantBit = lsb.get()
        def.mostSignificantBit = msb.get()
        return def
    }

}