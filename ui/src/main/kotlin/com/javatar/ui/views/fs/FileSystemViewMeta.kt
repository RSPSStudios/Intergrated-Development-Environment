package com.javatar.ui.views.fs

data class FileSystemViewMeta(val id: Int, val meta: MetaType) {
    enum class MetaType {
        INDEX,
        ARCHIVE,
        FILE
    }
}
