package com.javatar.fs

interface JDirectory<T : JNode> : JNode {

    fun nodes(): List<T>

}
