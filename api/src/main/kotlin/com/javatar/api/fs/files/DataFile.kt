package com.javatar.api.fs.files

import com.displee.cache.index.archive.Archive
import com.displee.cache.index.archive.file.File
import com.javatar.api.fs.JFile

class DataFile(override val id: Int, val archive: Archive) : JFile {
    override fun write(data: ByteArray): Boolean {
        archive.add(id, data)
        return true
    }

    override fun read(): ByteArray {
        return archive.file(id)?.data ?: byteArrayOf()
    }
}
