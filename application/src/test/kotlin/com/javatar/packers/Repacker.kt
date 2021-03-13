package com.javatar.packers

import com.displee.cache.CacheLibrary
import org.junit.jupiter.api.Test
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.readBytes

class Repacker {

    @ExperimentalPathApi
    @Test
    fun repackMaps() {

        val edgeLand = Path.of("/home/javatar/Downloads/wetransfer-1b2df4/624.dat")
        val edgeObj = Path.of("/home/javatar/Downloads/wetransfer-1b2df4/625.dat")
        val idkLand = Path.of("/home/javatar/Downloads/wetransfer-1b2df4/2000.dat")
        val idkObj = Path.of("/home/javatar/Downloads/wetransfer-1b2df4/2001.dat")

        val cache = CacheLibrary.create("/home/javatar/Downloads/realmCache")

        val edgeX: Int = 12342 shr 8
        val edgeY: Int = 12342 and 0xFF
        val idkX: Int = 2000 shr 8
        val idkY: Int = 2000 and 0xFF

        cache.put(5, "m${edgeX}_$edgeY", edgeLand.readBytes())
        cache.put(5, "l${edgeX}_$edgeY", edgeObj.readBytes())
        cache.put(5, "m${idkX}_$idkY", idkLand.readBytes())
        cache.put(5, "l${idkX}_$idkY", idkObj.readBytes())

        cache.update()

    }

}
