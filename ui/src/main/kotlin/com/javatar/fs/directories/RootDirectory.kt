package com.javatar.fs.directories

import com.displee.cache.CacheLibrary
import com.javatar.fs.JDirectory

class RootDirectory(val cache: CacheLibrary) : JDirectory<IndexDirectory> {
    override fun nodes(): List<IndexDirectory> {
        return cache.indices().mapIndexed { index, _ -> IndexDirectory(index, this) }
    }
}
