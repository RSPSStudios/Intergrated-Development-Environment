package com.javatar.plugin.definition.editor.fs

import com.javatar.api.fs.FileType
import com.javatar.api.fs.extensions.FileTypeExtension
import com.javatar.plugin.definition.editor.fs.types.ItemFileType
import org.pf4j.Extension

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

@Extension
class ItemFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return ItemFileType()
    }
}

