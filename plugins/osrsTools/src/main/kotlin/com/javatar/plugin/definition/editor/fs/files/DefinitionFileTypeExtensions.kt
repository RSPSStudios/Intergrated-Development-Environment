package com.javatar.plugin.definition.editor.fs.files

import com.javatar.api.fs.FileType
import com.javatar.api.fs.extensions.FileTypeExtension
import com.javatar.osrs.definitions.impl.NpcDefinition
import com.javatar.osrs.definitions.impl.ObjectDefinition
import com.javatar.osrs.definitions.impl.SequenceDefinition
import com.javatar.osrs.definitions.loaders.NpcLoader
import com.javatar.osrs.definitions.loaders.ObjectLoader
import com.javatar.osrs.definitions.loaders.SequenceLoader
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.fs.types.files.DefinitionFileType
import org.pf4j.Extension

@Extension
class ObjectsFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<ObjectDefinition, ObjectLoader>(
            6, 2, OldSchoolDefinitionManager.objects, "objects"
        )
    }
}

@Extension
class NpcsFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<NpcDefinition, NpcLoader>(
            9, 2, OldSchoolDefinitionManager.npcs, "npcs"
        )
    }
}

@Extension
class AnimationFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<SequenceDefinition, SequenceLoader>(
            12, 2, OldSchoolDefinitionManager.animations, "anims"
        )
    }
}