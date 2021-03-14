package com.javatar.plugin.definition.editor

import com.javatar.definition.DefinitionManager
import com.javatar.loader.*
import com.javatar.saver.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

object OldSchoolDefinitionManager {
    val items = DefinitionManager(ItemLoader(), ItemSaver())
    val models = DefinitionManager(ModelLoader(), ModelSaver())
    val textures = DefinitionManager(TextureLoader(), TextureSaver())
    val sprites = DefinitionManager(SpriteLoader(), SpriteSaver())
    val npcs = DefinitionManager(NpcLoader(), NpcSaver())
    val objects = DefinitionManager(ObjectLoader(), ObjectSaver())
    val varbits = DefinitionManager(VarbitLoader(), VarbitSaver())
    val enums = DefinitionManager(EnumLoader(), EnumSaver())
    val playerKit = DefinitionManager(KitLoader(), KitSaver())
    val hitsplats = DefinitionManager(HitSplatLoader(), HitSplatSaver())
    val areas = DefinitionManager(AreaLoader(), AreaSaver())
    val widgets = DefinitionManager(InterfaceLoader(), InterfaceSaver())
    val inventories = DefinitionManager(InventoryLoader(), InventorySaver())
    val overlays = DefinitionManager(OverlayLoader(), OverlaySaver())
    val underlays = DefinitionManager(UnderlayLoader(), UnderlaySaver())
    val params = DefinitionManager(ParamLoader(), ParamSaver())
    val scripts = DefinitionManager(ScriptLoader(), ScriptSaver())
    val animations = DefinitionManager(SequenceLoader(), SequenceSaver())
    val graphics = DefinitionManager(SpotAnimLoader(), SpotAnimSaver())
    val structs = DefinitionManager(StructLoader(), StructSaver())
}
