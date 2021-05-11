package com.javatar.plugin.definition.editor.ui.editor.cvars.model

import com.javatar.osrs.definitions.impl.VarbitDefinition
import com.javatar.osrs.tools.variables.VariableTools.bitCount
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
            if(it != null) {
                val type = variableType.get()
                when {
                    type === VariableType.BOOLEAN -> {
                        maxValue.set(1)
                        msb.set(lsb.get())
                    }
                    type === VariableType.CUSTOM -> {}
                    type === VariableType.INT -> {
                        val bits = Int.MAX_VALUE.bitCount()
                        if(lsb.get() + bits < 32) {
                            maxValue.set(Int.MAX_VALUE)
                        }
                    }
                    else -> {
                        if(lsb.get() + type.bitSize < 32) {
                            maxValue.set((1 shl type.bitSize) - 1)
                        }
                    }
                }
            }
        }


        maxValue.onChange {
            this.msb.set(lsb.get() + it.bitCount())
        }

        lsb.onChange {
            val value = maxValue.get()
            val requiredBits = value.bitCount()
            val end = it + requiredBits
            if (end <= 31) {
                msb.set(end)
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