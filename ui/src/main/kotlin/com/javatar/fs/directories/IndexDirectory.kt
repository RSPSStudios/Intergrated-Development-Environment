package com.javatar.fs.directories

import com.javatar.fs.JDirectory

class IndexDirectory(val nodeIndex: Int, val parent: RootDirectory) : JDirectory<ArchiveDirectory> {
    override fun nodes(): List<ArchiveDirectory> {
        return parent.cache.index(nodeIndex).archiveIds().map { ArchiveDirectory(it, this) }
    }
}
