package com.javatar.application

import com.javatar.terminal.TerminalManager

object TerminalApplication {

    const val VERSION = "0.1.0"

    @JvmStatic
    fun main(args: Array<String>) {
        TerminalManager(args).execute()
    }

}
