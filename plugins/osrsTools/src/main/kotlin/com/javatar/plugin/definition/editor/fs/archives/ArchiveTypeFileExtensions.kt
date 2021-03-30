package com.javatar.plugin.definition.editor.fs.archives

import com.javatar.api.fs.ArchiveType
import com.javatar.api.fs.extensions.ArchiveTypeExtension
import com.javatar.plugin.definition.editor.fs.types.archives.AnimationArchiveType
import com.javatar.plugin.definition.editor.fs.types.archives.NpcArchiveType
import com.javatar.plugin.definition.editor.fs.types.archives.ObjectArchiveType
import org.pf4j.Extension

@Extension
class ObjectArchiveTypeExtension : ArchiveTypeExtension {
    override fun createArchiveType(): ArchiveType {
        return ObjectArchiveType()
    }
}

@Extension
class NpcArchiveTypeExtension : ArchiveTypeExtension {
    override fun createArchiveType(): ArchiveType {
        return NpcArchiveType()
    }
}

@Extension
class AnimationArchiveTypeExtension : ArchiveTypeExtension {
    override fun createArchiveType(): ArchiveType {
        return AnimationArchiveType()
    }
}
