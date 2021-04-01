package com.javatar.api.fs.directories

import com.javatar.api.fs.JDirectory

class IndexDirectory(override val id: Int, val parent: RootDirectory) : JDirectory<ArchiveDirectory> {
    override fun nodes(): List<ArchiveDirectory> {
        return parent.cache.index(id).archiveIds().map { ArchiveDirectory(it, this) }
    }
}
