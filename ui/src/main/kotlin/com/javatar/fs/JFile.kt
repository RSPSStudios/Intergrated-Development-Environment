package com.javatar.fs

interface JFile : JNode {

    val id: Int
    fun write(data: ByteArray): Boolean
    fun read(): ByteArray

}
