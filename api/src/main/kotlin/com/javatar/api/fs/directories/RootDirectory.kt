package com.javatar.api.fs.directories

import com.displee.cache.CacheLibrary
import com.javatar.api.fs.JDirectory

class RootDirectory(val cache: CacheLibrary) : JDirectory<IndexDirectory> {
    override val id: Int = -1
    override fun nodes(): List<IndexDirectory> {
        return cache.indices().mapIndexed { index, _ -> IndexDirectory(index, this) }
    }
}
