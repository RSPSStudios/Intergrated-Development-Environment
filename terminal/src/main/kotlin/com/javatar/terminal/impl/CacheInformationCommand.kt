package com.javatar.terminal.impl

import com.displee.cache.CacheLibrary
import com.javatar.terminal.TerminalCommand
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

class CacheInformationCommand(parser: ArgParser) : TerminalCommand(parser) {

    val version by parser.flagging("-v", help = "Version of cache").default(false)
    val itemCount by parser.flagging("-i", help = "Item Definition Count").default(false)
    val npcCount by parser.flagging("-n", help = "Npc Definition Count").default(false)
    val objectCount by parser.flagging("-o", help = "Object Definition Count").default(false)
    val animationCount by parser.flagging("-a", help = "Animation Definition Count").default(false)
    val graphicsCount by parser.flagging("-g", help = "Graphic Definition Count").default(false)
    val all by parser.flagging("--a", help = "Show all information.").default(false)
    val cachePath by parser.positional("Cache path")

    override fun execute() {
        val cache = CacheLibrary.create(cachePath)
        if (version || all) {
            println(when {
                cache.isRS3() -> "RuneScape 3"
                cache.isOSRS() -> "Old School RuneScape"
                else -> "RuneScape 2"
            })
        }
        if(itemCount || all) {
            val count = cache.index(2).archive(10)?.fileIds()?.size
            println("Total Item Definitions: $count")
        }
        if(npcCount || all) {
            val count = cache.index(2).archive(9)?.fileIds()?.size
            println("Total Npc Definitions: $count")
        }
        if(objectCount || all) {
            val count = cache.index(2).archive(6)?.fileIds()?.size
            println("Total Object Definitions: $count")
        }
        if(animationCount || all) {
            val count = cache.index(2).archive(12)?.fileIds()?.size
            println("Total Animation Definitions: $count")
        }
        if(graphicsCount || all) {
            val count = cache.index(2).archive(13)?.fileIds()?.size
            println("Total Graphic Definitions: $count")
        }
    }
}
