package com.javatar.api.ui

import javafx.scene.image.PixelBuffer
import java.nio.IntBuffer

fun PixelBuffer<IntBuffer>.clearPixels() {

    val size = buffer.capacity()

    repeat(size) {

        buffer.put(it, 0)

    }

}