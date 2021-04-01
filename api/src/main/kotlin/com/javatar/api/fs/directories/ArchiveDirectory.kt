package com.javatar.api.fs.directories

import com.javatar.api.fs.JDirectory
import com.javatar.api.fs.JFile
import com.javatar.api.fs.files.DataFile

class ArchiveDirectory(override val id: Int, val parent: IndexDirectory) : JDirectory<JFile> {
    override fun nodes(): List<JFile> {
        val index = parent.parent.cache.index(parent.id)
        val archive = index.archive(id)
        return archive
            ?.fileIds()?.map {
                DataFile(
                    it,
                    archive
                )
            } ?: emptyList()
    }
}
