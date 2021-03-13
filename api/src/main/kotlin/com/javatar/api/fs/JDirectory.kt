package com.javatar.api.fs

interface JDirectory<T : JNode> : JNode {

    fun nodes(): List<T>

}
