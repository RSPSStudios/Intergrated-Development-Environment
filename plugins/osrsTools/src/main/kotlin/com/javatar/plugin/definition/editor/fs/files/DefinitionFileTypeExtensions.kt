package com.javatar.plugin.definition.editor.fs.files

import com.javatar.api.fs.ArchiveType
import com.javatar.api.fs.FileType
import com.javatar.api.fs.extensions.ArchiveTypeExtension
import com.javatar.api.fs.extensions.FileTypeExtension
import com.javatar.osrs.definitions.impl.*
import com.javatar.osrs.definitions.loaders.*
import com.javatar.plugin.definition.editor.OldSchoolDefinitionManager
import com.javatar.plugin.definition.editor.fs.types.files.DefinitionFileType
import com.javatar.plugin.definition.editor.fs.types.files.SpriteFileType
import com.javatar.plugin.definition.editor.fs.types.files.TextureFileType
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

@Extension
class AreaFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<AreaDefinition, AreaLoader>(
            35, 2, OldSchoolDefinitionManager.areas, "areas"
        )
    }
}

@Extension
class EnumFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<EnumDefinition, EnumLoader>(
            8, 2, OldSchoolDefinitionManager.enums, "enums"
        )
    }
}

@Extension
class HitSplatFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<HitSplatDefinition, HitSplatLoader>(
            32, 2, OldSchoolDefinitionManager.hitsplats, "hits"
        )
    }
}

@Extension
class InterfaceFileTypeExtension : ArchiveTypeExtension {
    override fun createArchiveType(): ArchiveType {
        return DefinitionFileType<InterfaceDefinition, InterfaceLoader>(
            -1, 3, OldSchoolDefinitionManager.widgets, "interfaces"
        )
    }
}

@Extension
class InventoryFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<InventoryDefinition, InventoryLoader>(
            5, 2, OldSchoolDefinitionManager.inventories, "inventories"
        )
    }
}

@Extension
class IdentityKitFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<KitDefinition, KitLoader>(
            3, 2, OldSchoolDefinitionManager.playerKit, "kits"
        )
    }
}

@Extension
class OverlayFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<OverlayDefinition, OverlayLoader>(
            4, 2, OldSchoolDefinitionManager.overlays, "overlays"
        )
    }
}

@Extension
class ParamFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<ParamDefinition, ParamLoader>(
            11, 2, OldSchoolDefinitionManager.params, "params"
        )
    }
}

@Extension
class ScriptFileTypeExtension : ArchiveTypeExtension {
    override fun createArchiveType(): ArchiveType {
        return DefinitionFileType<ScriptDefinition, ScriptLoader>(
            -1, 12, OldSchoolDefinitionManager.scripts, "cs2"
        )
    }
}

@Extension
class GraphicFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<SpotAnimDefinition, SpotAnimLoader>(
            13, 2, OldSchoolDefinitionManager.graphics, "graphics"
        )
    }
}

@Extension
class SpriteGroupFileTypeExtension : ArchiveTypeExtension {
    override fun createArchiveType(): ArchiveType {
        return SpriteFileType()
    }
}

@Extension
class StructFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<StructDefinition, StructLoader>(
            34, 2, OldSchoolDefinitionManager.structs, "structs"
        )
    }
}

@Extension
class TextureFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return TextureFileType()
    }
}

@Extension
class UnderlayFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<UnderlayDefinition, UnderlayLoader>(
            1, 2, OldSchoolDefinitionManager.underlays, "underlays"
        )
    }
}

@Extension
class VarbitFileTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<VarbitDefinition, VarbitLoader>(
            14, 2, OldSchoolDefinitionManager.varbits, "varbits"
        )
    }
}

@Extension
class WorldmapTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<WorldMapDefinition, WorldMapLoader>(
            -1, 16, OldSchoolDefinitionManager.worldmap, "worldmap"
        )
    }
}

@Extension
class HealthBarTypeExtension : FileTypeExtension {
    override fun createFileType(): FileType {
        return DefinitionFileType<HealthBarDefinition, HealthBarLoader>(
            33, 2, OldSchoolDefinitionManager.healthbars, "healthbars"
        )
    }
}