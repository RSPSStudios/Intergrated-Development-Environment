package com.javatar.plugin.definition.editor

import com.javatar.osrs.definitions.loaders.*
import com.javatar.plugin.definition.editor.managers.ConfigDefinitionManager

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

object OldSchoolDefinitionManager {
    val items = ConfigDefinitionManager(ItemLoader())
    val models = ConfigDefinitionManager(ModelLoader())
    val textures = ConfigDefinitionManager(TextureLoader())
    val sprites = ConfigDefinitionManager(SpriteLoader())
    val npcs = ConfigDefinitionManager(NpcLoader())
    val objects = ConfigDefinitionManager(ObjectLoader())
    val varbits = ConfigDefinitionManager(VarbitLoader())
    val varps = ConfigDefinitionManager(VarpLoader())
    val enums = ConfigDefinitionManager(EnumLoader())
    val playerKit = ConfigDefinitionManager(KitLoader())
    val hitsplats = ConfigDefinitionManager(HitSplatLoader())
    val areas = ConfigDefinitionManager(AreaLoader())
    val widgets = ConfigDefinitionManager(InterfaceLoader())
    val inventories = ConfigDefinitionManager(InventoryLoader())
    val overlays = ConfigDefinitionManager(OverlayLoader())
    val underlays = ConfigDefinitionManager(UnderlayLoader())
    val params = ConfigDefinitionManager(ParamLoader())
    val scripts = ConfigDefinitionManager(ScriptLoader())
    val animations = ConfigDefinitionManager(SequenceLoader())
    val graphics = ConfigDefinitionManager(SpotAnimLoader())
    val structs = ConfigDefinitionManager(StructLoader())
    val worldmap = ConfigDefinitionManager(WorldMapLoader())
    val healthbars = ConfigDefinitionManager(HealthBarLoader())
}
