package com.javatar.api.fs

interface JDirectory<T : JNode> : JNode {

    val id: Int
    fun nodes(): List<T>

}
