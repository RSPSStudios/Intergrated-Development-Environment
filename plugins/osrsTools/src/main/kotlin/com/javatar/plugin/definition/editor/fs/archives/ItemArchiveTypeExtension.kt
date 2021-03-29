package com.javatar.plugin.definition.editor.fs.archives

import com.javatar.api.fs.ArchiveType
import com.javatar.api.fs.extensions.ArchiveTypeExtension
import com.javatar.plugin.definition.editor.fs.types.archives.ItemArchiveType
import org.pf4j.Extension

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

@Extension
class ItemArchiveTypeExtension : ArchiveTypeExtension {
    override fun createArchiveType(): ArchiveType {
        return ItemArchiveType()
    }
}
