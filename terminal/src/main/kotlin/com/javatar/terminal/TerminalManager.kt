package com.javatar.terminal

import com.javatar.terminal.impl.CacheInformationCommand
import com.xenomachina.argparser.ArgParser

class TerminalManager(val args: Array<String>) {

    val commands = mutableMapOf<String, (ArgParser) -> TerminalCommand>()

    init {
        commands["cache"] = { CacheInformationCommand(it) }
    }

    fun execute() {
        val name = args[0]
        val args = this.args.copyOfRange(1, this.args.size)
        if(commands.containsKey(name)) {
            commands[name]!!(ArgParser(args)).execute()
        }
    }

}
