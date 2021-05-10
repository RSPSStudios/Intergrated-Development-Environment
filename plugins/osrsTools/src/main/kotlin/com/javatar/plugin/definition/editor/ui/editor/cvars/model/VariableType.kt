package com.javatar.plugin.definition.editor.ui.editor.cvars.model

enum class VariableType(val bitSize: Int) {

    BOOLEAN(1),
    BYTE(8),
    SHORT(16),
    INT(32),
    CUSTOM(0)

}