package com.javatar.fs.files

import com.displee.cache.index.archive.file.File
import com.javatar.fs.JFile

class DataFile(override val id: Int, val file: File?) : JFile {
    override fun write(data: ByteArray): Boolean {
        if (file != null) {
            file.data = data
            return true
        }
        return false
    }

    override fun read(): ByteArray {
        return if (file?.data != null) {
            file.data!!
        } else {
            byteArrayOf()
        }
    }
}
