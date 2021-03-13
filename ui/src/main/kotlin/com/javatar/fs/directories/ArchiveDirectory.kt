package com.javatar.fs.directories

import com.javatar.fs.JDirectory
import com.javatar.fs.JFile
import com.javatar.fs.files.DataFile

class ArchiveDirectory(val id: Int, val parent: IndexDirectory) : JDirectory<JFile> {
    override fun nodes(): List<JFile> {
        val index = parent.parent.cache.index(parent.nodeIndex)
        val archive = index.archive(id)
        return archive
            ?.fileIds()?.map {
                DataFile(
                    it,
                    archive.file(it)
                )
            } ?: emptyList()
    }
}
