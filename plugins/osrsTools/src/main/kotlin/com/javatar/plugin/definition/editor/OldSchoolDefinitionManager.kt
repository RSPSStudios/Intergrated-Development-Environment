package com.javatar.plugin.definition.editor

import com.javatar.osrs.definitions.DefinitionManager
import com.javatar.osrs.definitions.loaders.*

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

object OldSchoolDefinitionManager {
    val items = DefinitionManager(ItemLoader())
    val models = DefinitionManager(ModelLoader())
    val textures = DefinitionManager(TextureLoader())
    val sprites = DefinitionManager(SpriteLoader())
    val npcs = DefinitionManager(NpcLoader())
    val objects = DefinitionManager(ObjectLoader())
    val varbits = DefinitionManager(VarbitLoader())
    val enums = DefinitionManager(EnumLoader())
    val playerKit = DefinitionManager(KitLoader())
    val hitsplats = DefinitionManager(HitSplatLoader())
    val areas = DefinitionManager(AreaLoader())
    val widgets = DefinitionManager(InterfaceLoader())
    val inventories = DefinitionManager(InventoryLoader())
    val overlays = DefinitionManager(OverlayLoader())
    val underlays = DefinitionManager(UnderlayLoader())
    val params = DefinitionManager(ParamLoader())
    val scripts = DefinitionManager(ScriptLoader())
    val animations = DefinitionManager(SequenceLoader())
    val graphics = DefinitionManager(SpotAnimLoader())
    val structs = DefinitionManager(StructLoader())
    val worldmap = DefinitionManager(WorldMapLoader())
    val healthbars = DefinitionManager(HealthBarLoader())
}
